package com.fdc.common.exchange.newbusiness.model;

import com.fdc.common.util.CustomerType;

public class CustomerModel {
	
	private String employerId;
	private String customerName;
	private String customerNationalId;
	private CustomerType type;
	
	public CustomerModel(String employerId, String customerName, String customerNationalId, CustomerType type) {
		super();
		this.employerId = employerId;
		this.customerName = customerName;
		this.customerNationalId = customerNationalId;
		this.type = type;
	}

	public String getEmployerId() {
		return employerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerNationalId() {
		return customerNationalId;
	}

	public CustomerType getType() {
		return type;
	}
	
	
	
	
}
