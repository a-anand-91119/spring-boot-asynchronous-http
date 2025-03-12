package dev.notyouraverage.project.one.http.asynchronous_http_server.configurations;

import dev.notyouraverage.project.one.http.asynchronous_http_server.constants.Constants;
import dev.notyouraverage.project.one.http.asynchronous_http_server.configurations.utils.KafkaConfigurationUtils;
import dev.notyouraverage.project.one.http.asynchronous_http_server.core.JsonSerializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    @Bean(Constants.JSON_SERIALIZABLE_PUBLIC_CLUSTER_PRODUCER_FACTORY)
    public ProducerFactory<String, JsonSerializable> jsonSerializablePublicProducerFactory() {
        Map<String, Object> props = KafkaConfigurationUtils.buildCommonProducerConfigs(kafkaProperties);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(Constants.JSON_SERIALIZABLE_PUBLIC_KAFKA_TEMPLATE)
    public KafkaTemplate<String, JsonSerializable> jsonSerializablePublicKafkaTemplate(
            @Qualifier(Constants.JSON_SERIALIZABLE_PUBLIC_CLUSTER_PRODUCER_FACTORY) ProducerFactory<String, JsonSerializable> jsonSerializableProducerFactory
    ) {
        return new KafkaTemplate<>(jsonSerializableProducerFactory);
    }
}
