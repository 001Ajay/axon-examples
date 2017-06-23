package com.fdc.commandside.customer.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fdc.commandside.customer.integration.CustomerRegistryClient;
import com.fdc.commandside.customer.integration.CustomerResource;
import com.fdc.common.customer.model.CorporateCustomerModel;
import com.fdc.common.exchange.newbusiness.CreateEmployerAsCustomerCommand;

@Component
public class CustomerCommandHandler {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerCommandHandler.class);

	private Repository<Customer> repository;
	
	@Autowired
	private CustomerRegistryClient customerRegistryFeignClient; //in standalone testing this interface needs to be mockedO

	@Autowired
	@Qualifier("customerRepository")
	public void setRepository(Repository<Customer> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public void handleCreateEmployer(CreateEmployerAsCustomerCommand command) throws Exception {
		// Command processing logic
		LOG.debug("Customer id = " + command.getEmployerId());

		// Need to retrieve all customer data from cvr/cpr registry using cvr

		CorporateCustomerModel cust = createCorporateCustomerModel(command.getCvr());
		
		LOG.debug("Customer details = " + cust.toString());

		// Store the domain event in ES
		repository.newInstance(() -> {
			return new Customer(command.getAuditEntry(), command.getEmployerId(), cust, command.getAgreementId());
		});

	}

	// Get customer details from customer registry...using feign client
	private CorporateCustomerModel createCorporateCustomerModel(String nationalId) {

		CustomerResource response = customerRegistryFeignClient.getCustomerFromRegistryByNationalId(nationalId);

		CorporateCustomerModel cust = new CorporateCustomerModel(response.getCustomerId(), response.getCompanyName(),
				response.getNationalId(), response.getAddresses(), response.getTelephones(), response.getEmails(),
				response.getTaxInfo());
		
		return cust;
	}

}