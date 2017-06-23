package com.fdc.common.util;

public enum CommunicationUsageType {
	PERSONAL("Personal"),WORK("Work"),PRIMARY("Primary"),SECONDARY("Secondary");

	private String value;

	private CommunicationUsageType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
