package com.fdc.agreementProduct.web;

import java.util.List;

import com.fdc.common.agreementProduct.model.PolicyProductModel;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateAgreementProductRequest {

	@NonNull private String ovkAgreementNumber;
	@NonNull private String ovkAgreementCode;
	@NonNull private String agreementProductName;
	@NonNull private String agreementProductType;
	private List<PolicyProductModel> policyProducts;


}
