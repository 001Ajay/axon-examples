package com.fdc.customer.registry.web;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.fdc.customer.registry.aggregate.Customer;

public class CustomerResourceAssembler
		extends ResourceAssemblerSupport<Customer, CustomerResource> {

	

	public CustomerResourceAssembler(Class<?> controllerClass, Class<CustomerResource> resourceType) {
		super(controllerClass, resourceType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CustomerResource toResource(Customer customer) {
		CustomerResource resource = createResourceWithId(customer.getCustomerId(), customer);
		return resource;
	}

	@Override
	protected CustomerResource instantiateResource(Customer customer) {
		
		CustomerResource resource = new CustomerResource(customer.getNationalId());
		resource.setCompanyName(customer.getName());
		resource.setCustomerId(customer.getCustomerId());
		if (customer.getAddresses()!=null)
			resource.setAddresses(customer.getAddresses());
		if (customer.getTelephones()!=null)
			resource.setTelephones(customer.getTelephones());
		if (customer.getEmails()!=null)
			resource.setEmails(customer.getEmails());
		if (customer.getTaxInfo()!=null)
			resource.setTaxInfo(customer.getTaxInfo());
		return resource;
	}

	
}
