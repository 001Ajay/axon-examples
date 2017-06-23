package com.fdc.customer.web;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.fdc.customer.aggregate.CorporateCustomer;

public class CorporateCustomerResourceAssembler
		extends ResourceAssemblerSupport<CorporateCustomer, CorporateCustomerResource> {

	

	public CorporateCustomerResourceAssembler(Class<?> controllerClass, Class<CorporateCustomerResource> resourceType) {
		super(controllerClass, resourceType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CorporateCustomerResource toResource(CorporateCustomer customer) {
		CorporateCustomerResource resource = createResourceWithId(customer.getCustomerId(), customer);
		return resource;
	}

	@Override
	protected CorporateCustomerResource instantiateResource(CorporateCustomer customer) {
		
		CorporateCustomerResource resource = new CorporateCustomerResource(customer.getCustomerId(),customer.getCompanyName(),customer.getCvr());
		
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
