package com.fdc.commandside.agreement.integration;

import java.util.List;

import com.fdc.common.agreement.model.PolicyProductModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgreementProductResource {

	private String agreementProductNumber;
	private String ovkAgreementNumber;
	private String ovkAgreementCode;
	private String agreementProductName;
	private String agreementProductType;
	private List<PolicyProductModel> policyProducts;


}
