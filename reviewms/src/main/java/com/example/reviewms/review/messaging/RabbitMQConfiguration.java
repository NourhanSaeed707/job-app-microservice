package com.example.reviewms.review.messaging;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

public class RabbitMQConfiguration {
    public Queue companyRatingQueue () {
        return new Queue("companyRatingQueue");
    }

    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
