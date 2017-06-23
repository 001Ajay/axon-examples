package com.fdc.common.agreement.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;
import lombok.NonNull;

@Data
@XStreamAlias("customer")
public class CustomerModel {

	private String customerNumber;
	@NonNull private String name;
	
}
