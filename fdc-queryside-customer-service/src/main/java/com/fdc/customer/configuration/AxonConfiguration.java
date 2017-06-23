package com.fdc.customer.configuration;

import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fdc.common.event.AbstractEvent;
import com.rabbitmq.client.Channel;

/**
 * A configuration for axonframework. Axonframework is used to support
 * eventsourcing and CQRS.
 *
 */
@Configuration
public class AxonConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(AxonConfiguration.class);

	@Autowired
    private TransactionManager axonTransactionManager;
	
	@Bean(name = "CustomerMessageSource")
	public SpringAMQPMessageSource messageSourceSaga(Serializer serializer) {
		return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {

			@RabbitListener(queues = "#{'${fdc.integration.customer-read}'}")
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				LOG.debug("Message received = " + message.toString());
				super.onMessage(message, channel);
			}
		};
	}
	
	@Bean
	public TransactionManagingInterceptor<EventMessage<AbstractEvent>> transactionManagingInterceptor(TransactionManager axonTransactionManager){
		return new TransactionManagingInterceptor<EventMessage<AbstractEvent>>(axonTransactionManager);
	}
}
