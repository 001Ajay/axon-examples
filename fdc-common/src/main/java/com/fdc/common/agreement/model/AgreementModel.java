package com.fdc.common.agreement.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XStreamAlias("agreementProduct")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgreementModel {

	private String OVKCode;
	private String agreementProductNumber;
	private String agreementProductName;
	private String agreementType;
	private String agreementProductType;	
	@XStreamImplicit private List<PolicyProductModel> policyProducts;
	
}
