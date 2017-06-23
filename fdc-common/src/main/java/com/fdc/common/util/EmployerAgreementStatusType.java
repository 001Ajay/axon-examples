package com.fdc.common.util;

public enum EmployerAgreementStatusType {
	INITIATED("Initiated"), CREATED("Created");

	private String value;

	private EmployerAgreementStatusType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
