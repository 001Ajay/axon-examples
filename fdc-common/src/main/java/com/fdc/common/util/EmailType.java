package com.fdc.common.util;

public enum EmailType {
	PUBLIC("Public"),CORPORATE("Corporate");

	private String value;

	private EmailType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
