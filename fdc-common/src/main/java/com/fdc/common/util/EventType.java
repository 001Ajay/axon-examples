package com.fdc.common.util;

public enum EventType {
	DOMAIN_EVENT("exchange.event.domain"), EXCHANGE_EVENT("exchange.event.integration");

	private String value;

	private EventType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
