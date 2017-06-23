package com.fdc.common.util;

public enum AddressUsageType {
	PRESENT("Present"), PERMANENT("Permanent"),WORK("Work"),PRIMARY("Primary"),SECONDARY("Secondary");

	private String value;

	private AddressUsageType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
