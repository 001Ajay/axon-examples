package com.fdc.common.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.saga.ResourceInjector;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.spring.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.spring.saga.SpringResourceInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration for axonframework. Axonframework is used to support
 * eventsourcing and CQRS.
 *
 */
@Configuration
public class AxonConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(AxonConfiguration.class);


	@Autowired
	@Qualifier("distributedCommandBus")
	private CommandBus distributedCommandBus;	

	
	@SuppressWarnings("rawtypes")
	@Bean("commandGateway")
	public CommandGatewayFactoryBean commandGateway(){
		
		CommandGatewayFactoryBean commandGatewayFactoryBean=new CommandGatewayFactoryBean<>();
		commandGatewayFactoryBean.setCommandBus(distributedCommandBus);
		return new CommandGatewayFactoryBean();
	}

	/*
	 * @Bean public Serializer serializer() { return new JacksonSerializer(); }
	 */

	/*@Bean
	public RoutingKeyResolver routingKeyResolver() {
		return new FDCRoutingKeyResolver();
	}*/

	@Bean
	public ResourceInjector resourceInjector() {
		return new SpringResourceInjector();
	}

	/*@SuppressWarnings("rawtypes")
	@Bean
	public TransactionManagingInterceptor<EventMessage> transactionManagingInterceptor(
			TransactionManager axonTransactionManager) {
		return new TransactionManagingInterceptor<EventMessage>(axonTransactionManager);
	}*/

	

}
