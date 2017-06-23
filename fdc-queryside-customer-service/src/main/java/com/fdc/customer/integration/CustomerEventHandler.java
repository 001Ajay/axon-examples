package com.fdc.customer.integration;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdc.commandside.agreegate.event.EmployerAgreementCreatedEvent;
import com.fdc.commandside.customer.event.CorporateCustomerCreatedEvent;
import com.fdc.common.util.EmployerAgreementStatusType;
import com.fdc.customer.aggregate.CorporateCustomer;
import com.fdc.customer.aggregate.CorporateCustomerRepository;

@ProcessingGroup("CustomerEventProcessor")
@Component
public class CustomerEventHandler {

	private static final long serialVersionUID = 5948996680443725871L;

	private final static Logger LOG = LoggerFactory.getLogger(CustomerEventHandler.class);
	
	@Autowired
	CorporateCustomerRepository corporateCustomerRepository;

	@EventHandler
	public void handle(CorporateCustomerCreatedEvent event){
		LOG.debug("Received event CorporateCustomerCreatedEvent id [{}]",event.getId());

		//Create and Save the entity
		CorporateCustomer customer = new CorporateCustomer(event.getId(),event.getCorporateCustomer().getCompanyName(), event.getCorporateCustomer().getCvr());
		if (event.getCorporateCustomer().getAddresses()!=null)
			customer.setAddresses(event.getCorporateCustomer().getAddresses());
		if (event.getCorporateCustomer().getTelephones()!=null)
			customer.setTelephones(event.getCorporateCustomer().getTelephones());
		if (event.getCorporateCustomer().getEmails()!=null)
			customer.setEmails(event.getCorporateCustomer().getEmails());
		if (event.getCorporateCustomer().getTaxInfo()!=null)
			customer.setTaxInfo(event.getCorporateCustomer().getTaxInfo());
		
		LOG.debug("Create corporateCustomer with detail - ",customer.toString());
		corporateCustomerRepository.save(customer);
	}
	
}
