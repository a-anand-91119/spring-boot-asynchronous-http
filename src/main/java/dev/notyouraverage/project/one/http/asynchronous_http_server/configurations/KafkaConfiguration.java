package dev.notyouraverage.project.one.http.asynchronous_http_server.configurations;

import dev.notyouraverage.project.one.http.asynchronous_http_server.constants.Constants;
import dev.notyouraverage.project.one.http.asynchronous_http_server.configurations.utils.KafkaConfigurationUtils;
import dev.notyouraverage.project.core.JsonSerializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.micrometer.KafkaTemplateObservation;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    @Bean(Constants.JSON_SERIALIZABLE_PRODUCER_FACTORY)
    public ProducerFactory<String, JsonSerializable> jsonSerializablePublicProducerFactory() {
        Map<String, Object> props = KafkaConfigurationUtils.buildCommonProducerConfigs(kafkaProperties);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        System.out.println(kafkaProperties.getProperties());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(Constants.JSON_SERIALIZABLE_KAFKA_TEMPLATE)
    public KafkaTemplate<String, JsonSerializable> jsonSerializablePublicKafkaTemplate(
            @Qualifier(Constants.JSON_SERIALIZABLE_PRODUCER_FACTORY) ProducerFactory<String, JsonSerializable> jsonSerializableProducerFactory
    ) {
        KafkaTemplate<String, JsonSerializable> kafkaTemplate = new KafkaTemplate<>(jsonSerializableProducerFactory);
        kafkaTemplate.setObservationEnabled(true);
        kafkaTemplate
                .setObservationConvention(new KafkaTemplateObservation.DefaultKafkaTemplateObservationConvention());
        return kafkaTemplate;
    }

    @Bean(Constants.JSON_SERIALIZABLE_CONSUMER_FACTORY)
    public ConsumerFactory<String, JsonSerializable> salesforceDelayedQueueConsumerFactory() {
        Map<String, Object> props = KafkaConfigurationUtils.buildCommonConsumerConfigs(kafkaProperties);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class.getName());
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put("spring.json.trusted.packages", "dev.notyouraverage.project.*");

        ErrorHandlingDeserializer<String> keyErrorHandlingDeserializer = new ErrorHandlingDeserializer<>();
        ErrorHandlingDeserializer<JsonSerializable> valueErrorHandlingDeserializer = new ErrorHandlingDeserializer<>();
        return new DefaultKafkaConsumerFactory<>(props, keyErrorHandlingDeserializer, valueErrorHandlingDeserializer);
    }

    @Bean(Constants.JSON_SERIALIZABLE_CONCURRENT_LISTENER_CONTAINER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<String, JsonSerializable> listenerFactory(
            @Qualifier(Constants.JSON_SERIALIZABLE_CONSUMER_FACTORY) ConsumerFactory<String, JsonSerializable> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, JsonSerializable> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setObservationEnabled(true);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
