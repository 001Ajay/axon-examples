package com.fdc.agreement.web;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.fdc.agreement.aggregate.EmployerAgreement;

public class EmployerAgreementResourceAssembler
		extends ResourceAssemblerSupport<EmployerAgreement, EmployerAgreementResource> {

	

	public EmployerAgreementResourceAssembler(Class<?> controllerClass, Class<EmployerAgreementResource> resourceType) {
		super(controllerClass, resourceType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EmployerAgreementResource toResource(EmployerAgreement empAgreement) {
		EmployerAgreementResource resource = createResourceWithId(empAgreement.getAgreementId(), empAgreement);
		return resource;
	}

	@Override
	protected EmployerAgreementResource instantiateResource(EmployerAgreement empAgreement) {
		
		EmployerAgreementResource resource = new EmployerAgreementResource(empAgreement.getAgreementId(),empAgreement.getCvr(),
												empAgreement.getPnumber(),empAgreement.getCustomerNumber(),empAgreement.getName(),empAgreement.getAgreements());
		return resource;
	}

	
}
