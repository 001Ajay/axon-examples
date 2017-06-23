package com.fdc.commandside.scheme.web;

import com.fdc.common.scheme.model.AgreementModel;
import com.fdc.common.scheme.model.EmployerModel;
import com.fdc.common.scheme.model.SchemeModel;

public class RegisterEmployerRequest {

	private SchemeModel schemeData;
	private AgreementModel agreementData;
	private EmployerModel employerData;

	public RegisterEmployerRequest() {
	}

	public RegisterEmployerRequest(SchemeModel schemeData, AgreementModel agreementData, EmployerModel employerData) {
		super();
		this.schemeData = schemeData;
		this.agreementData = agreementData;
		this.employerData = employerData;
	}

	public SchemeModel getSchemeData() {
		return schemeData;
	}

	public void setSchemeData(SchemeModel schemeData) {
		this.schemeData = schemeData;
	}

	public AgreementModel getAgreementData() {
		return agreementData;
	}

	public void setAgreementData(AgreementModel agreementData) {
		this.agreementData = agreementData;
	}

	public EmployerModel getEmployerData() {
		return employerData;
	}

	public void setEmployerData(EmployerModel employerData) {
		this.employerData = employerData;
	}

}
