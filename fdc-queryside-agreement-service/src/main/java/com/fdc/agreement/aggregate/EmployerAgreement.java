package com.fdc.agreement.aggregate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fdc.common.agreement.model.AgreementModel;
import com.fdc.common.util.EmployerAgreementStatusType;

import lombok.Data;
import lombok.NonNull;

@Data
@Document(collection = "employerAgreement")
public class EmployerAgreement {

	private static final Logger LOG = LoggerFactory.getLogger(EmployerAgreement.class);
	
	@Id @NonNull private String agreementId;
	@Indexed @NonNull private String cvr;
	@NonNull private String pnumber;
	@Indexed @NonNull private String customerNumber;
	@NonNull private String name;
	@NonNull private List<AgreementModel> agreements;
	@NonNull private EmployerAgreementStatusType status;

}