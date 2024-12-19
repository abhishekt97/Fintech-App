package com.app.profile.config;

import com.app.profile.domain.event.UserRegisteredEventHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    public static final String TOPIC = "userRegisteredTopic";
    public static final String QUEUE_NAME = "user.registered.profile";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME, false);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC);
    }
    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("user.#");
    }
    @Bean
    public SimpleMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory,
                                                            MessageListenerAdapter messageListenerAdapter)//helps send the msg content to your own object
    {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueueNames(QUEUE_NAME);
        listenerContainer.setMessageListener(messageListenerAdapter);

        return listenerContainer;
    }

    @Bean
    public MessageListenerAdapter userRegisteredListenerAdapter(UserRegisteredEventHandler userRegisteredEventHandler){
        return new MessageListenerAdapter(userRegisteredEventHandler, "handleUserRegistration");
    }
}
