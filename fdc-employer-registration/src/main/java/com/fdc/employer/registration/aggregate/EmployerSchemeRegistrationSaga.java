package com.fdc.employer.registration.aggregate;

import java.util.UUID;

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
import com.fdc.commandside.scheme.event.EmployerSchemeRegistrationEvent;
import com.fdc.common.exchange.newbusiness.AssociateEmployerWithSchemeCommand;
import com.fdc.common.exchange.newbusiness.CreateEmployerAsCustomerCommand;
import com.fdc.common.exchange.newbusiness.model.CustomerModel;
import com.fdc.common.util.CustomerType;

//@ProcessingGroup("EmployerRegistrationSagaEventProcessor")
//@Component
public class EmployerSchemeRegistrationSaga {

	private static final long serialVersionUID = 5948996680443725871L;

	private final static Logger LOG = LoggerFactory.getLogger(EmployerSchemeRegistrationSaga.class);

	private boolean employerResigrationDone;
	private boolean employerCreationDone;

	@Autowired
	private transient CommandGateway commandGateway;

	/*@StartSaga
	@SagaEventHandler(associationProperty = "id")
	public void handle(EmployerSchemeRegistrationEvent event) {
		// Create the Employer Corporate Customer Id
		String employerId = getEmployerId();
		// Associate the employer id with saga so that it can be used later once
		// employer creation is done in Customer domain
		SagaLifecycle.associateWith("employerId", employerId);

		LOG.debug("In EmployerSchemeRegistrationSaga, Scheme id = " + event.getId());
		LOG.debug("In EmployerSchemeRegistrationSaga, Employer id = " + employerId);

		// Generate command for corporate customer(employer) creation for
		// Customer aggregate & publish to command bus
		CustomerModel employer = new CustomerModel(employerId, event.getEmployerModel().getName(),
				event.getEmployerModel().getRegistrationNumber(), CustomerType.CORPORATE_CUSTOMER);
		CreateEmployerAsCustomerCommand employerCreate = new CreateEmployerAsCustomerCommand(event.getAuditEntry(),
				employerId, employer, event.getId());
		commandGateway.send(employerCreate);

	}

	@SagaEventHandler(associationProperty = "employerId")
	public void handle(CorporateCustomerCreatedEvent event) {
		employerCreationDone = true;
		LOG.debug("Got customer creation event....for employer id = " + event.getEmployerId());

		AssociateEmployerWithSchemeCommand associateEmployerWithSchemeCommand = new AssociateEmployerWithSchemeCommand(
				event.getAuditEntry(), event.getId(), event.getCorporateCustomer().getSchemeId());
		commandGateway.send(associateEmployerWithSchemeCommand);
	}

	@SagaEventHandler(associationProperty = "id")
	@EndSaga
	public void handle(EmployerAgreementCreatedEvent event) {
		employerResigrationDone = true;
		LOG.debug("In EmployerSchemeRegistrationSaga ends....for Scheme id = " + event.getId());
	}

	private String getEmployerId() {
		return UUID.randomUUID().toString();
	}*/
}
