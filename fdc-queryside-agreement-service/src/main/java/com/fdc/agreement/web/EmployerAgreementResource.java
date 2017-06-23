package com.fdc.agreement.web;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fdc.common.agreement.model.AgreementModel;

import lombok.Data;
import lombok.NonNull;

@Data
public class EmployerAgreementResource extends ResourceSupport{

	@NonNull private String employerAgreementId;
	@NonNull private String cvr;
	@NonNull private String pnumber;
	@NonNull private String customerNumber;
	@NonNull private String name;
	@NonNull private List<AgreementModel> agreements;
}
