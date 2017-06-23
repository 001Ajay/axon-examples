package com.fdc.common.util;

public enum TelephoneType {
	LANDLINE("LandLine"),MOBILE("Mobile");

	private String value;

	private TelephoneType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
