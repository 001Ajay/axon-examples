package com.fdc.common.util;

public enum EmployerRegistrationType {
	CUSTOMER_TO_BE_CREATED("CUSTOMER_TO_BE_CREATED"), CUSTOMER_TO_BE_ASSOCIATED("CUSTOMER_TO_BE_ASSOCIATED");

	private String value;

	private EmployerRegistrationType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
