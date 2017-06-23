package com.fdc.commandside.agreement.configuration;

import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fdc.commandside.agreement.aggregate.EmployerAgreement;
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
	public EmployerAgreement employerAgreement() {
		return new EmployerAgreement();
	}

	@Bean
	public AggregateFactory<EmployerAgreement> agreementAggregateFactory() {
		SpringPrototypeAggregateFactory<EmployerAgreement> aggregateFactory = new SpringPrototypeAggregateFactory<>();
		aggregateFactory.setPrototypeBeanName("employerAgreement");

		return aggregateFactory;
	}

	@Bean
	public Repository<EmployerAgreement> agreementRepository() {
		/*
		 * EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new
		 * EventCountSnapshotTriggerDefinition( snapshotter, 50);
		 * 
		 * CachingEventSourcingRepository<Scheme> repository = new
		 * CachingEventSourcingRepository<>( schemeAggregateFactory(),
		 * eventStore, cache, snapshotTriggerDefinition);
		 */
		EventSourcingRepository<EmployerAgreement> repository = new EventSourcingRepository<>(agreementAggregateFactory(),
				eventStore);
		return repository;
	}
	
	/*@Bean
    public EventProcessor aggregateEventProcessor(Agreement agreement) {
        SubscribingEventProcessor eventProcessor = new SubscribingEventProcessor("aggregateEventProcessor",
                                                                                 new SimpleEventHandlerInvoker(agreement),
                                                                                 eventStore, new AsynchronousEventProcessingStrategy());
        eventProcessor.start();

        return eventProcessor;
    }*/

	@Bean
	public TransactionManagingInterceptor<EventMessage<AbstractEvent>> transactionManagingInterceptor(TransactionManager axonTransactionManager){
		return new TransactionManagingInterceptor<EventMessage<AbstractEvent>>(axonTransactionManager);
	}
}
