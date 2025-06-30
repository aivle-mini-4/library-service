package aivle.global.config;

import aivle.infra.messaging.KafkaProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(KafkaProcessor.class)
public class KafkaConfig {
}
