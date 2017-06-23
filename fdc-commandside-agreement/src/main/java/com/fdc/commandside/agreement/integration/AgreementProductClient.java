package com.fdc.commandside.agreement.integration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface AgreementProductClient {
	//search agreement product based on ovk code
	@RequestMapping(method = RequestMethod.GET, value = "/agreementProducts/searchByOVKCode")
	AgreementProductResource getAgreementProduct(@RequestParam(value="ovkCode") String ovkCode);
}
