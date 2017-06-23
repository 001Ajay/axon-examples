package com.fdc.commandside.customer.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fdc.commandside.customer.event.CorporateCustomerCreatedEvent;
import com.fdc.common.customer.model.CorporateCustomerModel;
import com.fdc.common.customer.model.IndividualCustomerModel;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.CustomerType;

import lombok.Data;
import lombok.NoArgsConstructor;


@AggregateRoot
@Data
@NoArgsConstructor
public class Customer {

	private static final Logger LOG = LoggerFactory.getLogger(Customer.class);

	@AggregateIdentifier
	private String customerId;
	private CustomerType type;
	private CorporateCustomerModel corporateCustomer;
	private IndividualCustomerModel individualCustomer;

	
	public Customer(AuditEntry auditEntry, String customerId, CorporateCustomerModel customerData, String agreementId) {
		super();
		// generate a domain event for corporate customer creation
		CorporateCustomerCreatedEvent event = new CorporateCustomerCreatedEvent(customerId, auditEntry,customerData,agreementId);
		apply(event);
	}
	
	
	@EventSourcingHandler
	public void on(CorporateCustomerCreatedEvent event) {
		this.customerId = event.getId();
		this.corporateCustomer = event.getCorporateCustomer();
		LOG.debug("CorporateCustomerCreatedEvent handler,  with id [{}]",this.customerId);
	}
	
}