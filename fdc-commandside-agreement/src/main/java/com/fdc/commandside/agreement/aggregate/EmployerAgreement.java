package com.fdc.commandside.agreement.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fdc.commandside.agreegate.event.EmployerAgreementCreatedEvent;
import com.fdc.commandside.agreegate.event.EmployerAgreementToBeCreatedEvent;
import com.fdc.common.agreement.model.AgreementModel;
import com.fdc.common.agreement.model.CustomerModel;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.AgreementType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AggregateRoot
@Data
@NoArgsConstructor
public class EmployerAgreement {

	private static final Logger LOG = LoggerFactory.getLogger(EmployerAgreement.class);

	@AggregateIdentifier
	private String agreementId;
	private String cvr;
	private String pnumber;
	private CustomerModel employer;
	private List<AgreementModel> agreements;

	public EmployerAgreement(String agreementId, String cvr, String pnumber, 
			List<AgreementModel> agreements,CustomerModel employer, AuditEntry auditEntry) {
		super();
		this.agreementId = agreementId;
		this.cvr = cvr;
		this.pnumber = pnumber;
		this.agreements = agreements;
		this.employer = employer;
		
		// generate a domain event - this will save event in ES as well as start
		// a saga
		EmployerAgreementToBeCreatedEvent event = new EmployerAgreementToBeCreatedEvent(agreementId, cvr, pnumber,
				agreements, employer, auditEntry);
		LOG.info("Event to be published with id - " + event.getId());
		apply(event);
	}

	// Domain logic - generate an event for employer creation
	public void associateEmployerWithCustomerDetails(String employerId,AuditEntry auditEntry) {
		// update customer details
		this.employer.setCustomerNumber(employerId);//update this data at this moment

		// Complete the agreement creation
		
		EmployerAgreementCreatedEvent event = new EmployerAgreementCreatedEvent(auditEntry, this.agreementId, employer);
		LOG.info("Event to be published - " + event.toString());
		apply(event);
	}

	@EventSourcingHandler
	public void on(EmployerAgreementToBeCreatedEvent event) {
		this.agreementId = event.getId();
		this.employer = event.getCustomer();
		this.agreements = event.getAgreementModels();

		LOG.info("AgreementToBeRegisteredEvent handler, with id [{}] ", this.agreementId);

	}
	
	@EventSourcingHandler
	public void on(EmployerAgreementCreatedEvent event) {
		this.agreementId = event.getId();
		this.employer = event.getEmployer();

		LOG.info("AgreementRegisteredEvent handler, with employer id [{}] ", this.employer.getCustomerNumber());

	}

}