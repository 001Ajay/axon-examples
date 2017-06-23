package com.fdc.commandside.customer.configuration;

import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fdc.commandside.customer.aggregate.Customer;
import com.fdc.common.event.AbstractEvent;

/**
 * A configuration for axonframework. Axonframework is used to support
 * eventsourcing and CQRS.
 *
 */
@Configuration
public class AxonConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(AxonConfiguration.class);

	@Autowired
	private EventStore eventStore;

	@Bean
	@Scope("prototype")
	public Customer customer() {
		return new Customer();
	}

	@Bean
	public AggregateFactory<Customer> customerAggregateFactory() {
		SpringPrototypeAggregateFactory<Customer> aggregateFactory = new SpringPrototypeAggregateFactory<>();
		aggregateFactory.setPrototypeBeanName("customer");

		return aggregateFactory;
	}

	@Bean
	public Repository<Customer> customerRepository() {
		/*
		 * EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new
		 * EventCountSnapshotTriggerDefinition( snapshotter, 50);
		 * 
		 * CachingEventSourcingRepository<Scheme> repository = new
		 * CachingEventSourcingRepository<>( schemeAggregateFactory(),
		 * eventStore, cache, snapshotTriggerDefinition);
		 */
		EventSourcingRepository<Customer> repository = new EventSourcingRepository<>(customerAggregateFactory(),
				eventStore);
		return repository;
	}

	/*
	 * @Bean public SpringAMQPMessageSource messageSourceCustomer(Serializer
	 * serializer) { return new SpringAMQPMessageSource(new
	 * DefaultAMQPMessageConverter(serializer)) {
	 * 
	 * @RabbitListener(queues =
	 * "#{'${fdc.integration.employer-registration-queue}'}")
	 * 
	 * @Override public void onMessage(Message message, Channel channel) throws
	 * Exception { System.out.println("Message received = " +
	 * message.toString()); super.onMessage(message, channel); } }; }
	 */

	@Bean
	public TransactionManagingInterceptor<EventMessage<AbstractEvent>> transactionManagingInterceptor(
			TransactionManager axonTransactionManager) {
		return new TransactionManagingInterceptor<EventMessage<AbstractEvent>>(axonTransactionManager);
	}

	/*
	 * @Bean public EventProcessor
	 * schemeExchangeEventProcessor(SpringAMQPMessageSource
	 * messageSourceCustomer) { SubscribingEventProcessor eventProcessor = new
	 * SubscribingEventProcessor("schemeExchangeEventProcessor", new
	 * SimpleEventHandlerInvoker(customerEventHandler), messageSourceCustomer);
	 * 
	 * eventProcessor.registerInterceptor(new
	 * TransactionManagingInterceptor<>(axonTransactionManager));
	 * eventProcessor.start();
	 * 
	 * return eventProcessor; }
	 */

}
