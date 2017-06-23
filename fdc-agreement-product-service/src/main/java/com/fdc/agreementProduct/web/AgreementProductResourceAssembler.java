package com.fdc.agreementProduct.web;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.fdc.agreementProduct.aggregate.AgreementProduct;


public class AgreementProductResourceAssembler
		extends ResourceAssemblerSupport<AgreementProduct, AgreementProductResource> {

	

	public AgreementProductResourceAssembler(Class<?> controllerClass, Class<AgreementProductResource> resourceType) {
		super(controllerClass, resourceType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AgreementProductResource toResource(AgreementProduct agreementProduct) {
		AgreementProductResource resource = createResourceWithId(agreementProduct.getAgreementProductNumber(), agreementProduct);
		return resource;
	}

	@Override
	protected AgreementProductResource instantiateResource(AgreementProduct agreementProduct) {
		
		AgreementProductResource resource = new AgreementProductResource(agreementProduct.getAgreementProductNumber(),agreementProduct.getOvkAgreementNumber(),
				agreementProduct.getOvkAgreementCode(),agreementProduct.getAgreementProductName(),agreementProduct.getAgreementProductType(),
				agreementProduct.getPolicyProducts());
		return resource;
	}

	
}
