package br.com.bruno.emailservice.configs;

import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Bean
    public SimpleMessageConverter simpleMessageConverter() {
        var messageConverter = new SimpleMessageConverter();
        messageConverter.setAllowedListPatterns(List.of(
                "models.*", "java.util.*", "java.time.*", "java.lang.*"
        ));
        return messageConverter;
    }
}
