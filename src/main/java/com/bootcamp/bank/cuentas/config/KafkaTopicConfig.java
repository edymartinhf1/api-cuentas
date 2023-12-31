package com.bootcamp.bank.cuentas.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Clase generadora de Topico Kafka
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic bootcampTopic() {

        return TopicBuilder.name("monederomovil").build();
    }

    public NewTopic bootcampTopicP2P() {
        return TopicBuilder.name("monederoP2PMovil").build();
    }

}