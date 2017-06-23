package com.fdc.common.agreementProduct.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class PolicyProductModel {

	@NonNull private String policyProductCode;
	@NonNull private String policyProductName;
	@NonNull private String policyProductType;

	
}
