package dev.notyouraverage.project.one.http.asynchronous_http_server.services.impl;

import dev.notyouraverage.project.one.http.asynchronous_http_server.constants.Constants;
import dev.notyouraverage.project.one.http.asynchronous_http_server.core.JsonSerializable;
import dev.notyouraverage.project.one.http.asynchronous_http_server.dtos.kafka.output.ProcessRequestPayload;
import dev.notyouraverage.project.one.http.asynchronous_http_server.services.MainService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {

    private final KafkaTemplate<String, JsonSerializable> kafkaTemplate;
    private final String requestTopic;

    public MainServiceImpl(@Qualifier(Constants.JSON_SERIALIZABLE_PUBLIC_KAFKA_TEMPLATE) KafkaTemplate<String, JsonSerializable> jsonPublicKafkaTemplate, KafkaTemplate<String, JsonSerializable> kafkaTemplate,
                           @Value("${project.kafka.requestTopic.name}") String requestTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.requestTopic = requestTopic;
    }

    @Override
    public void processRequest(String requestId) {
        this.kafkaTemplate.send(this.requestTopic, ProcessRequestPayload.builder().build());
    }
}
