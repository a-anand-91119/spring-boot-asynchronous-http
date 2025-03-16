package dev.notyouraverage.project.one.http.asynchronous_http_server.listeners;

import dev.notyouraverage.project.core.dtos.kafka.ProcessedResponsePayload;
import dev.notyouraverage.project.one.http.asynchronous_http_server.constants.Constants;
import dev.notyouraverage.project.one.http.asynchronous_http_server.helpers.BlockRequestHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessedResponseListener {

    private final BlockRequestHelper blockRequestHelper;

    @KafkaListener(id = Constants.LISTENER_RESPONSE_MESSAGES, topics = "${project.kafka.responseTopic.name}", containerFactory = Constants.JSON_SERIALIZABLE_CONCURRENT_LISTENER_CONTAINER_FACTORY)
    public void onMessage(
            @Payload ProcessedResponsePayload responsePayload,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition
    ) {
        log.info("Received Response (key={}, partition={}): {}", key, partition, responsePayload);
        blockRequestHelper.setResponsePayload(responsePayload.getRequestId(), responsePayload);
    }

}
