package com.fdc.commandside.scheme.configuration;

import org.axonframework.amqp.eventhandling.RoutingKeyResolver;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fdc.common.util.EventType;
import com.fdc.common.util.FDCRoutingKeyResolver;
import com.mysql.jdbc.Constants;

@Configuration
public class RabbitMQConfig {
	
	@Bean
	public RoutingKeyResolver routingKeyResolver() {
		return new FDCRoutingKeyResolver();
	}

	@Bean
	public Exchange exchangeScheme() {
		return ExchangeBuilder.topicExchange("FDC-SCHEME-EXCHANGE").durable(true).build();
	}
	
	@Bean
	public Queue queue() {
		return QueueBuilder.durable("SCHEME_READ").build();
	}

	
	@Bean
	public Binding bindingQueueWithSchemeExchange() {
		//This is done so that depending upon the package type of the event the routing will happen. If you want to change the 
		//routing key from package to something else please define the bean public RoutingKeyResolver routingKeyResolver()
		return BindingBuilder.bind(queue()).to(exchangeScheme()).with(EventType.DOMAIN_EVENT.getValue()).noargs();
	}

	@Autowired
	public void configure(AmqpAdmin admin) {		
		admin.declareExchange(exchangeScheme());
		admin.declareQueue(queue());
		admin.declareBinding(bindingQueueWithSchemeExchange());
	}
}
