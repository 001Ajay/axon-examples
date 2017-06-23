package com.fdc.agreementProduct.web;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fdc.common.agreementProduct.model.PolicyProductModel;

import lombok.Data;
import lombok.NonNull;

@Data
public class AgreementProductResource extends ResourceSupport{

	@NonNull private String agreementProductNumber;
	@NonNull private String ovkAgreementNumber;
	@NonNull private String ovkAgreementCode;
	@NonNull private String agreementProductName;
	@NonNull private String agreementProductType;
	@NonNull private List<PolicyProductModel> policyProducts;


}
