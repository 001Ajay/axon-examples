package com.fdc.common.util;

public enum CustomerStatusType {
	CREATED("Created"), PUBLISHED("Published");

	private String value;

	private CustomerStatusType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
