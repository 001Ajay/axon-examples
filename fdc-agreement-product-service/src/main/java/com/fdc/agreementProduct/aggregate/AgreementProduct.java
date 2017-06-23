package com.fdc.agreementProduct.aggregate;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fdc.common.agreementProduct.model.PolicyProductModel;

import lombok.Data;
import lombok.NonNull;

@Data
@Document(collection = "agreementProduct")
public class AgreementProduct {

	@Id
	@NonNull private String agreementProductNumber;
	@NonNull private String ovkAgreementNumber;
	@NonNull private String ovkAgreementCode;
	@NonNull private String agreementProductName;
	@NonNull private String agreementProductType;
	private List<PolicyProductModel> policyProducts;
	
}
