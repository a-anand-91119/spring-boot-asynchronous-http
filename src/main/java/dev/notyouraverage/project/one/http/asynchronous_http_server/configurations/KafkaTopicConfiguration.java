package dev.notyouraverage.project.one.http.asynchronous_http_server.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public KafkaAdmin.NewTopics asyncRequestTopics(
            @Value("${project.kafka.requestTopic.name}") String requestTopic,
            @Value("${project.kafka.requestTopic.partitions}") Integer partitions,
            @Value("${project.kafka.requestTopic.replicas}") Short replicas
    ) {
        return new KafkaAdmin.NewTopics(new NewTopic(requestTopic, partitions, replicas));
    }

    @Bean
    public KafkaAdmin.NewTopics asyncResponseTopics(
            @Value("${project.kafka.responseTopic.name}") String responseTopic,
            @Value("${project.kafka.responseTopic.partitions}") Integer partitions,
            @Value("${project.kafka.responseTopic.replicas}") Short replicas
    ) {
        return new KafkaAdmin.NewTopics(new NewTopic(responseTopic, partitions, replicas));
    }
}
