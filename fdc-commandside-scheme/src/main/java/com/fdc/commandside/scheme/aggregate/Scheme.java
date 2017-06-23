package com.fdc.commandside.scheme.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.UUID;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fdc.commandside.scheme.event.EmployerSchemePublishedEvent;
import com.fdc.commandside.scheme.event.EmployerSchemeRegistrationEvent;
import com.fdc.common.exchange.newbusiness.EmployerToBeCreatedEvent;
import com.fdc.common.exchange.newbusiness.model.CustomerModel;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.scheme.model.AgreementModel;
import com.fdc.common.scheme.model.EmployerModel;
import com.fdc.common.scheme.model.SchemeModel;
import com.fdc.common.util.CustomerType;
import com.fdc.common.util.SchemeStatusType;

@AggregateRoot
public class Scheme {

	private static final Logger LOG = LoggerFactory.getLogger(Scheme.class);

	@AggregateIdentifier
	private String schemeId;
	private SchemeStatusType status;
	private SchemeModel schemeData;
	private AgreementModel aggrementData;
	private EmployerModel customerData;

	@SuppressWarnings("UnusedDeclaration")
	public Scheme() {

	}

	public Scheme(AuditEntry auditEntry, String schemeId, SchemeModel schemeData, AgreementModel aggrementData,
			EmployerModel customerData) {
		super();
		// generate a domain event - this will save event in ES as well as start
		// a saga
		apply(new EmployerSchemeRegistrationEvent(schemeId, auditEntry, schemeData, aggrementData, customerData));
	}

	// Domain logic - generate an event for employer creation
	public void createCustomerForEmployer(AuditEntry auditEntry, String employerId, EmployerModel customerData,
			String schemeId) {
		// any domain logic here....

		// Create a new event for customer service
		CustomerModel employer = new CustomerModel(employerId, customerData.getName(),
				customerData.getRegistrationNumber(), CustomerType.CORPORATE_CUSTOMER);
		EmployerToBeCreatedEvent employerEvent = new EmployerToBeCreatedEvent(auditEntry, schemeId, employer);
		LOG.debug("Event to be published - " + employerEvent.toString());
		apply(employerEvent);
	}

	// Domain logic - generate an event for employer creation
	public void associateEmployerWithScheme(AuditEntry auditEntry, String schemeId, String employerId) {
		// any domain logic here....

		// Create event
		EmployerSchemePublishedEvent event = new EmployerSchemePublishedEvent(auditEntry,schemeId,employerId);
		LOG.debug("Event to be published - " + event.toString());
		apply(event);
	}

	@EventSourcingHandler
	public void on(EmployerSchemeRegistrationEvent event) {
		this.schemeId = event.getId();
		this.status = SchemeStatusType.CREATED;
		this.schemeData = event.getSchemeModel();
		this.aggrementData = event.getAgreementModel();
		LOG.debug("EmployerSchemeRegistrationEvent handler, with id " +
		 this.schemeId + " and status " + this.status
		 + ", Scheme code = " + this.schemeData.getCode() + ", agreement code = "
		 + this.aggrementData.getCode());

	}

	@EventSourcingHandler
	public void on(EmployerToBeCreatedEvent event) {
		this.status = SchemeStatusType.EMPLOYER_TO_BE_ASSOCIATED;
		this.customerData = new EmployerModel(event.getEmployer().getCustomerName(),
				event.getEmployer().getCustomerNationalId());
		LOG.debug("EmployerToBeCreatedEvent handler,  with status " + this.status);
	}
	
	@EventSourcingHandler
	public void on(EmployerSchemePublishedEvent event) {
		this.status = SchemeStatusType.PUBLISHED;
		LOG.debug("EmployerSchemePublishedEvent handler,  with status " + this.status);
	}
	
	

	public String getSchemeId() {
		return schemeId;
	}

	public SchemeStatusType getStatus() {
		return status;
	}

	public SchemeModel getSchemeData() {
		return schemeData;
	}

	public AgreementModel getAggrementData() {
		return aggrementData;
	}

	public EmployerModel getCustomerData() {
		return customerData;
	}

	private String getEmployerId() {
		return UUID.randomUUID().toString();
	}

}