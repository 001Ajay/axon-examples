package com.fdc.commandside.customer.web;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fdc.commandside.customer.command.CreateEmployerCommand;
import com.fdc.commandside.customer.integration.CustomerRegistryClient;
import com.fdc.commandside.customer.integration.CustomerResource;
import com.fdc.common.customer.model.CorporateCustomerModel;
import com.fdc.common.model.AuditEntry;

@RestController
public class CustomerController {

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private CustomerRegistryClient customerRegistryFeignClient;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/createCorporateCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<String> create(@RequestBody CreateCorporateCustomerRequest request, Principal principal) {
		LOG.debug(CreateCorporateCustomerRequest.class.getSimpleName() + " request received");

		AuditEntry auditEntry = new AuditEntry("Rana1");
		

		CreateEmployerCommand command = createEmployerCommand(auditEntry,request.getRegistrationNumber());
		commandGateway.sendAndWait(command);

		LOG.debug("Command processed with corporate customer ID [{}] ", command.getCustomerId());

		return new ResponseEntity<String>(command.getCustomerId(), HttpStatus.CREATED);
	}

	// Get all the agreement product details based on OVK code...using feign
	// client
	private CreateEmployerCommand createEmployerCommand(AuditEntry auditEntry, String nationalId) {
		String customerId = getCustomerId();
		CustomerResource response = customerRegistryFeignClient.getCustomerFromRegistryByNationalId(nationalId);

		CorporateCustomerModel cust = new CorporateCustomerModel(response.getCustomerId(),response.getCompanyName(),response.getNationalId(),
				response.getAddresses(),response.getTelephones(),response.getEmails(),response.getTaxInfo());
		CreateEmployerCommand command = new CreateEmployerCommand(auditEntry, customerId,cust);
		return command;
	}

	private String getCustomerId() {
		return UUID.randomUUID().toString();
	}
}
