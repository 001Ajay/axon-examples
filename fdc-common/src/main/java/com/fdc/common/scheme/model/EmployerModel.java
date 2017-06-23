package com.fdc.common.scheme.model;

import java.util.List;

import com.fdc.common.util.AgreementType;

public class EmployerModel {

	private String registrationNumber;
	private String name;
	
	
	
	public EmployerModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployerModel(String name, String registrationNumber) {
		super();
		this.name = name;
		this.registrationNumber = registrationNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getName() {
		return name;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
