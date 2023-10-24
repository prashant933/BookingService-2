package com.prashant.service;

import com.prashant.model.BookingEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailPublisherImpl implements IEmailPublisher{
    @Value("${rabbitmq.reminder.service.queue.name}")
    public String QUEUE_NAME;
    @Value("${rabbitmq.reminder.service.exchange.name}")
    public String EXCHANGE_NAME;
    @Value("${rabbitmq.reminder.service.routing.key}")
    public String ROUTING_KEY;
    private final Logger logger = LoggerFactory.getLogger(EmailPublisherImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishMessage(BookingEmail bookingEmail) {
        logger.info(String.format("Publishing message with exchange: %s and routing key: %s",
                EXCHANGE_NAME, ROUTING_KEY));
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, bookingEmail);
        logger.info("Successfully published message to Rabbit MQ");
    }
}
