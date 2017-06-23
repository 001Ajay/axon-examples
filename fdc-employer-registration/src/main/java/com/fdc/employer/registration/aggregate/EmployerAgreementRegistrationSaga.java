package com.fdc.employer.registration.aggregate;

import java.security.Timestamp;
import java.time.Duration;
import java.time.Instant;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdc.commandside.agreegate.event.EmployerAgreementCreatedEvent;
import com.fdc.commandside.agreegate.event.EmployerAgreementToBeCreatedEvent;
import com.fdc.commandside.customer.event.CorporateCustomerCreatedEvent;
import com.fdc.common.exchange.newbusiness.AssociateEmployerWithAgreementCommand;
import com.fdc.common.exchange.newbusiness.CreateEmployerAsCustomerCommand;
import com.fdc.common.exchange.newbusiness.model.CustomerModel;
import com.fdc.common.util.CustomerType;
import com.fdc.common.util.EmployerRegistrationType;

@ProcessingGroup("EmployerRegistrationSagaEventProcessor")
@Component
public class EmployerAgreementRegistrationSaga {

	private static final long serialVersionUID = 5948996680443725871L;

	private final static Logger LOG = LoggerFactory.getLogger(EmployerAgreementRegistrationSaga.class);

	private String status;
	private Instant startTime;

	@Autowired
	private transient CommandGateway commandGateway;

	@StartSaga
	@SagaEventHandler(associationProperty = "id")
	public void handle(EmployerAgreementToBeCreatedEvent event) {
		LOG.info("Starting EmployerAgreementRegistrationSaga with [{}] event",event.getClass().getName());
		//Record the start time
		startTime = Instant.now();
		// Create the Employer Corporate Customer Id
		String employerId = getEmployerId();
		// Associate the employer id with saga so that it can be used later once
		// employer creation is done in Customer domain
		SagaLifecycle.associateWith("employerId", employerId);

		LOG.info("In EmployerAgreementRegistrationSaga, Agreement id = " + event.getId());
		LOG.info("In EmployerAgreementRegistrationSaga, Employer id = " + employerId);

		// Generate command for corporate customer(employer) creation for
		// Customer aggregate & publish to command bus
		CustomerModel employer = new CustomerModel(employerId, event.getCustomer().getCustomerNumber(),
				event.getCvr(), CustomerType.CORPORATE_CUSTOMER);
		CreateEmployerAsCustomerCommand employerToCreate = new CreateEmployerAsCustomerCommand(employerId, 
				event.getCvr(), event.getCustomer().getName(),event.getId());
		employerToCreate.setAuditEntry(event.getAuditEntry());
		// change the state
		this.status = EmployerRegistrationType.CUSTOMER_TO_BE_CREATED.getValue();
		// Now send
		commandGateway.send(employerToCreate);

	}

	@SagaEventHandler(associationProperty = "employerId")
	public void handle(CorporateCustomerCreatedEvent event) {
		LOG.info("Got customer creation event....for employer id = " + event.getEmployerId());

		AssociateEmployerWithAgreementCommand associateEmployerWithAgreementCommand = 
				new AssociateEmployerWithAgreementCommand(event.getEmployerId(),event.getAgreementId());
		// change the state
		this.status = EmployerRegistrationType.CUSTOMER_TO_BE_ASSOCIATED.getValue();
		//now send
		commandGateway.send(associateEmployerWithAgreementCommand);
	}

	@SagaEventHandler(associationProperty = "id")
	@EndSaga
	public void handle(EmployerAgreementCreatedEvent event) {
		//Now calculate the difference in time
		Instant endTime = Instant.now();
		Duration timeToComplete = Duration.between(startTime, endTime);
		
		LOG.info("In EmployerAgreementCreatedEvent ends....for agreement id {}, start instant {} and end instant {}",
				event.getId(),startTime.getNano(),endTime.getNano());
		LOG.info("{} saga with id {} ends, time taken {} millisec","Employer Registration",event.getId(), timeToComplete.toMillis());
	}
	
	private String getEmployerId() {
		// A 6 digit random number
		return ""+((int)(Math.random()*900000)+100000);
		//return UUID.randomUUID().toString();
	}
}
