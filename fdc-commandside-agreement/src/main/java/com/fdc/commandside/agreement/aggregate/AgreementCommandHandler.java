package com.fdc.commandside.agreement.aggregate;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fdc.commandside.agreement.command.StartEmployerAgreementCreationCommand;
import com.fdc.commandside.agreement.integration.AgreementProductClient;
import com.fdc.commandside.agreement.integration.AgreementProductResource;
import com.fdc.common.agreement.model.AgreementModel;
import com.fdc.common.agreement.model.CustomerModel;
import com.fdc.common.exchange.newbusiness.AssociateEmployerWithAgreementCommand;

@Component
public class AgreementCommandHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AgreementCommandHandler.class);

	private Repository<EmployerAgreement> repository;

	@Autowired
	EventBus eventBus;
	
	@Autowired
	AgreementProductClient agreementProductFeignClient;

	@Autowired
	@Qualifier("agreementRepository")
	public void setRepository(Repository<EmployerAgreement> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public void handleStartEmployerAgreementCreation(StartEmployerAgreementCreationCommand command) throws Exception {
		// Command processing logic

		// create employer model
		CustomerModel employer = new CustomerModel(command.getName());
		// set dummy id
		employer.setCustomerNumber("Not Set");

		// Create agreement for employer with type OVK
		repository.newInstance(() -> {
			return new EmployerAgreement(command.getAgreementId(), command.getCvr(), command.getPnumber(),
					getAgreements(command.getOvks()), employer, command.getAuditEntry());
		});
		LOG.info("Command handling for handleStartEmployerAgreementCreation is done... ");
	}

	@CommandHandler
	public void handleAssociateEmployerWithAgreement(AssociateEmployerWithAgreementCommand command) throws Exception {
		Aggregate<EmployerAgreement> agreement = repository.load(command.getAgreementId());
		agreement.execute(aggregateRoot -> {
			aggregateRoot.associateEmployerWithCustomerDetails(command.getEmployerId(), command.getAuditEntry());
		});
	}

	// Get all the agreement product details based on OVK code...using feign
	// client
	private List<AgreementModel> getAgreements(List<String> ovkCodes) {
		List<AgreementModel> agreementProducts = new ArrayList<AgreementModel>();
		AgreementModel model;
		AgreementProductResource response;
		for (String ovkCode : ovkCodes) {
			// get response from agreement product service
			response = agreementProductFeignClient.getAgreementProduct(ovkCode);
			LOG.info("Response from agreement product service = " + response.toString());
			// now convert it
			model = new AgreementModel(ovkCode, response.getAgreementProductNumber(),
					response.getAgreementProductName(), response.getAgreementProductType(),
					response.getAgreementProductType(), response.getPolicyProducts());
			agreementProducts.add(model);
		}

		return agreementProducts;
	}

}