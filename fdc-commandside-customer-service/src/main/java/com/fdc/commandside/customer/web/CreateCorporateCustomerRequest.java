package com.fdc.commandside.customer.web;

public class CreateCorporateCustomerRequest {

	private String name;
	private String registrationNumber;

	public CreateCorporateCustomerRequest() {
	}

	
	
	public CreateCorporateCustomerRequest(String name, String registrationNumber) {
		super();
		this.name = name;
		this.registrationNumber = registrationNumber;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	
}
