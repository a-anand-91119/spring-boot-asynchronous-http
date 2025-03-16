package dev.notyouraverage.project.one.http.asynchronous_http_server.services.impl;

import dev.notyouraverage.project.core.dtos.kafka.ProcessedResponsePayload;
import dev.notyouraverage.project.one.http.asynchronous_http_server.constants.Constants;
import dev.notyouraverage.project.core.JsonSerializable;
import dev.notyouraverage.project.core.dtos.kafka.ProcessRequestPayload;
import dev.notyouraverage.project.one.http.asynchronous_http_server.dtos.response.ProcessedRequestResponse;
import dev.notyouraverage.project.one.http.asynchronous_http_server.helpers.BlockRequestHelper;
import dev.notyouraverage.project.one.http.asynchronous_http_server.services.MainService;
import dev.notyouraverage.project.one.http.asynchronous_http_server.transformers.ProcessedRequestDataTransformer;
import dev.notyouraverage.project.one.http.asynchronous_http_server.utils.CompletableFutureUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MainServiceImpl implements MainService {

    private final KafkaTemplate<String, JsonSerializable> kafkaTemplate;

    private final String requestTopic;

    private final BlockRequestHelper blockRequestHelper;

    private final ProcessedRequestDataTransformer processedRequestDataTransformer;


    public MainServiceImpl(
            @Qualifier(Constants.JSON_SERIALIZABLE_KAFKA_TEMPLATE) KafkaTemplate<String, JsonSerializable> kafkaTemplate,
            @Value("${project.kafka.requestTopic.name}") String requestTopic,
            BlockRequestHelper blockRequestHelper, ProcessedRequestDataTransformer processedRequestDataTransformer
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.requestTopic = requestTopic;
        this.blockRequestHelper = blockRequestHelper;
        this.processedRequestDataTransformer = processedRequestDataTransformer;
    }

    @Override
    public ProcessedRequestResponse processRequest(String requestId, @NotBlank String name) {
        ProcessRequestPayload processRequestPayload = ProcessRequestPayload.builder()
                .requestId(requestId)
                .name(name)
                .build();
        blockRequestHelper.initializeLock(requestId);
        kafkaTemplate.send(requestTopic, requestId, processRequestPayload);
        try {
            blockRequestHelper.lockThread(requestId);
            return processedRequestDataTransformer.convertToProcessedRequestResponse(blockRequestHelper.removeResponsePayload(requestId));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
