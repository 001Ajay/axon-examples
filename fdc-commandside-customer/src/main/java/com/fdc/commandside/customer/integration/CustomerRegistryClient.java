package com.fdc.commandside.customer.integration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



public interface CustomerRegistryClient {
	//search customer based on cpr/cvr number
	@RequestMapping(method = RequestMethod.GET, value = "/registry/customers/searchByCustomerNationalId")
	CustomerResource getCustomerFromRegistryByNationalId(@RequestParam(value="nationalId") String nationalId);
}
