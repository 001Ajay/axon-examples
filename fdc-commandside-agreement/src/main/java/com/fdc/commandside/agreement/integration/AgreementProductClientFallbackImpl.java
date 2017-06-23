package com.fdc.commandside.agreement.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class AgreementProductClientFallbackImpl implements AgreementProductFeignClient {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public AgreementProductResource getAgreementProduct(String ovkCode) {
		
		AgreementProductResource response = new AgreementProductResource();
		response.setOvkAgreementCode(ovkCode);
		LOG.debug("Response from fallback agreement product service = " + response.toString());
		return response;
	}

}
