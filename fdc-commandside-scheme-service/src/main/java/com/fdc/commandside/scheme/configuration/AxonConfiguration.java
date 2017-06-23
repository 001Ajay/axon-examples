package com.fdc.commandside.scheme.configuration;

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

import com.fdc.commandside.scheme.aggregate.Scheme;
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
	public Scheme scheme() {
		return new Scheme();
	}

	@Bean
	public AggregateFactory<Scheme> schemeAggregateFactory() {
		SpringPrototypeAggregateFactory<Scheme> aggregateFactory = new SpringPrototypeAggregateFactory<>();
		aggregateFactory.setPrototypeBeanName("scheme");

		return aggregateFactory;
	}

	@Bean
	public Repository<Scheme> schemeRepository() {
		/*
		 * EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new
		 * EventCountSnapshotTriggerDefinition( snapshotter, 50);
		 * 
		 * CachingEventSourcingRepository<Scheme> repository = new
		 * CachingEventSourcingRepository<>( schemeAggregateFactory(),
		 * eventStore, cache, snapshotTriggerDefinition);
		 */
		EventSourcingRepository<Scheme> repository = new EventSourcingRepository<>(schemeAggregateFactory(),
				eventStore);
		return repository;
	}

	@Bean
	public TransactionManagingInterceptor<EventMessage<AbstractEvent>> transactionManagingInterceptor(TransactionManager axonTransactionManager){
		return new TransactionManagingInterceptor<EventMessage<AbstractEvent>>(axonTransactionManager);
	}
}
