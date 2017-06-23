package com.fdc.commandside.agreement.aggregate;

import java.util.List;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fdc.common.agreement.model.AgreementModel;
import com.fdc.common.agreement.model.CustomerModel;
import com.fdc.common.util.AgreementType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberAgreement {

	private static final Logger LOG = LoggerFactory.getLogger(MemberAgreement.class);

	@AggregateIdentifier
	private String agreementId;
	private String cvr;
	private CustomerModel employee;
	private List<AgreementModel> agreementProducts;
	private AgreementType agreementType;


}