package com.fdc.common.agreement.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;
import lombok.NonNull;

@XStreamAlias("policyProduct")
@Data
public class PolicyProductModel {

	@NonNull private String policyProductCode;
	@NonNull private String policyProductName;
	@NonNull private String policyProductType;

	
}
