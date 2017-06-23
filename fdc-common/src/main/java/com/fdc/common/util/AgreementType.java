package com.fdc.common.util;

public enum AgreementType {
	OVK("OVK"), INVIVIDUAL("Individual");

	private String value;

	private AgreementType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
