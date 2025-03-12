package dev.notyouraverage.project.one.http.asynchronous_http_server.configurations.utils;

import lombok.experimental.UtilityClass;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.Map;

@UtilityClass
public class KafkaConfigurationUtils {

    public static Map<String, Object> buildCommonProducerConfigs(KafkaProperties kafkaProperties) {
        return kafkaProperties.buildProducerProperties(null);
    }
}
