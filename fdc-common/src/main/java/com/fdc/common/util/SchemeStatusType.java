package com.fdc.common.util;

public enum SchemeStatusType {
	CREATED("Created"), EMPLOYER_TO_BE_ASSOCIATED("EmployerToBeAssociated"),PUBLISHED("Published");

	private String value;

	private SchemeStatusType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
