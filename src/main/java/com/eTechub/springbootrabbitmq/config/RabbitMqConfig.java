package com.eTechub.springbootrabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    //spring bean for rabbit mq queue

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    //spring bean for rabbit mq exchange

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }


    //spring bean for queue(to store json properties)
    @Bean
        public Queue jsonQueue(){
        return new Queue(jsonQueue);
    }


    //bind between queue and exchange  using routing key

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }


    //bind between json queue and exchange  using routing key

    @Bean
    public Binding JsonBinding() {
        return BindingBuilder.bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }

    //create a message converter

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
    //map the converter to the rabbit mq to be able to support and send json message

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    //connectionFactory
    //rabbitTemplate
    //RabbitAdmin....these 3 is automatically configure this beans so they wont be configured manually


}
