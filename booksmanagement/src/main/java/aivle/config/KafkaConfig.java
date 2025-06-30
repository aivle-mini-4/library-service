package aivle.config;

import aivle.infrastructure.messaging.KafkaProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(KafkaProcessor.class)
public class KafkaConfig {
}
