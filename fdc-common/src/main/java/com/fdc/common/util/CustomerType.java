package com.fdc.common.util;

public enum CustomerType {
	CORPORATE_CUSTOMER("Employer"), INVIVIDUAL_CUSTOMER("Member");

	private String value;

	private CustomerType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
