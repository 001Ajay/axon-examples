package com.fdc.employer.registration.configuration;

import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventProcessor;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.axonframework.eventhandling.saga.AbstractSagaManager;
import org.axonframework.eventhandling.saga.AnnotatedSagaManager;
import org.axonframework.eventhandling.saga.ResourceInjector;
import org.axonframework.eventhandling.saga.SagaRepository;
import org.axonframework.eventhandling.saga.repository.AnnotatedSagaRepository;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.axonframework.spring.saga.SpringResourceInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fdc.common.event.AbstractEvent;
import com.fdc.employer.registration.aggregate.EmployerAgreementRegistrationSaga;
import com.fdc.employer.registration.aggregate.EmployerRegistration;
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
	private EventStore eventStore;

	@Autowired
	private SagaStore<Object> sagaStore;
	
	
	@Autowired
    private TransactionManager axonTransactionManager;
	
	@Autowired
	private ResourceInjector resourceInjector;
	

	@Bean
	public ResourceInjector resourceInjector() {
		return new SpringResourceInjector();
	}
	
	
	@Bean
	@Scope("prototype")
	public EmployerRegistration employerRegistration() {
		return new EmployerRegistration();
	}

	@Bean
	public AggregateFactory<EmployerRegistration> employerRegistrationAggregateFactory() {
		SpringPrototypeAggregateFactory<EmployerRegistration> aggregateFactory = new SpringPrototypeAggregateFactory<>();
		aggregateFactory.setPrototypeBeanName("employerRegistration");

		return aggregateFactory;
	}

	@Bean
	public Repository<EmployerRegistration> employerRegistrationRepository() {
		/*
		 * EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new
		 * EventCountSnapshotTriggerDefinition( snapshotter, 50);
		 * 
		 * CachingEventSourcingRepository<Scheme> repository = new
		 * CachingEventSourcingRepository<>( schemeAggregateFactory(),
		 * eventStore, cache, snapshotTriggerDefinition);
		 */
		EventSourcingRepository<EmployerRegistration> repository = new EventSourcingRepository<>(employerRegistrationAggregateFactory(),
				eventStore);
		return repository;
	}


	/*@Bean
	public AbstractSagaManager<EmployerSchemeRegistrationSaga> employerRegistrationSagaManager() {
		LOG.debug("employerRegistrationSagaManager created");
		return new AnnotatedSagaManager<>(EmployerSchemeRegistrationSaga.class, employerRegistrationSagaRepository());
	}

	@Bean(name = "EmployerRegistrationSagaRepository")
	public SagaRepository<EmployerSchemeRegistrationSaga> employerRegistrationSagaRepository() {
		LOG.debug("employerRegistrationSagaRepository created");
		return new AnnotatedSagaRepository<>(EmployerSchemeRegistrationSaga.class, sagaStore, resourceInjector);
	}*/
	
	@Bean(name = "EmployerRegistrationSagaManager")
	public AbstractSagaManager<EmployerAgreementRegistrationSaga> employerRegistrationSagaManager() {
		LOG.debug("employerRegistrationSagaManager created");
		return new AnnotatedSagaManager<>(EmployerAgreementRegistrationSaga.class, employerRegistrationSagaRepository());
	}

	@Bean(name = "EmployerRegistrationSagaRepository")
	public SagaRepository<EmployerAgreementRegistrationSaga> employerRegistrationSagaRepository() {
		LOG.debug("employerRegistrationSagaRepository created");
		return new AnnotatedSagaRepository<>(EmployerAgreementRegistrationSaga.class, sagaStore, resourceInjector);
	}

	@Bean(name = "EmployerRegistrationSagaEventProcessor")
    public EventProcessor employerRegistrationSagaEventProcessor(
    		AbstractSagaManager<EmployerAgreementRegistrationSaga> EmployerRegistrationSagaManager
    		,SpringAMQPMessageSource EmployerRegistrationSagaQueue) {
        SubscribingEventProcessor eventProcessor = new SubscribingEventProcessor(
                "employerRegistrationSagaEventProcessor",
                EmployerRegistrationSagaManager,
                EmployerRegistrationSagaQueue);
        eventProcessor.registerInterceptor(new TransactionManagingInterceptor<>(axonTransactionManager));
        eventProcessor.start();

        return eventProcessor;
    }
	
	@Bean(name = "EmployerRegistrationSagaQueue")
	public SpringAMQPMessageSource messageSourceSaga(Serializer serializer) {
		return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {

			@RabbitListener(queues = "#{'${fdc.integration.employer-registration-queue}'}")
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				System.out.println("Message received = " + message.toString());
				super.onMessage(message, channel);
			}
		};
	}
	
	@Bean
	public TransactionManagingInterceptor<EventMessage<AbstractEvent>> transactionManagingInterceptor(TransactionManager axonTransactionManager){
		return new TransactionManagingInterceptor<EventMessage<AbstractEvent>>(axonTransactionManager);
	}
    

}
