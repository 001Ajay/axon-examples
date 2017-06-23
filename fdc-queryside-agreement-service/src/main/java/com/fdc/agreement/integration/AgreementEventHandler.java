package com.fdc.agreement.integration;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdc.agreement.aggregate.EmployerAgreementRepository;
import com.fdc.agreement.aggregate.EmployerAgreement;
import com.fdc.commandside.agreegate.event.EmployerAgreementCreatedEvent;
import com.fdc.commandside.agreegate.event.EmployerAgreementToBeCreatedEvent;
import com.fdc.common.util.EmployerAgreementStatusType;

@ProcessingGroup("AgreementEventProcessor")
@Component
public class AgreementEventHandler {

	private static final long serialVersionUID = 5948996680443725871L;

	private final static Logger LOG = LoggerFactory.getLogger(AgreementEventHandler.class);
	
	@Autowired
	EmployerAgreementRepository employerAgreementRepository;

	@EventHandler
	public void handle(EmployerAgreementToBeCreatedEvent event){
		LOG.debug("Received event id [{}]",event.getId());

		//Create and Save the entity
		EmployerAgreement agreement = new EmployerAgreement(event.getId(), event.getCvr(), event.getPnumber(), 
				event.getCustomer().getCustomerNumber(), event.getCustomer().getName(),event.getAgreementModels(),EmployerAgreementStatusType.INITIATED);
		employerAgreementRepository.save(agreement);
	}
	
	@EventHandler
	public void handle(EmployerAgreementCreatedEvent event){
		LOG.debug("Received event id [{}]",event.getId());
		//Get the agreement from read store
		EmployerAgreement agreement = employerAgreementRepository.findByAgreementId(event.getId());
		if(null!=agreement){
			LOG.debug("Found employer agreement with cvr [{}]",agreement.getCvr());
			//update employer details, status and save again
			agreement.setCustomerNumber(event.getEmployer().getCustomerNumber());
			agreement.setName(event.getEmployer().getName());			
			agreement.setStatus(EmployerAgreementStatusType.CREATED);
			
			employerAgreementRepository.save(agreement);
		}
		

	}
	
}
