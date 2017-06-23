package com.fdc.common.customer.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class MailAddressModel {

	@NonNull private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String addressLine5;
	@NonNull private String city;
	@NonNull private String state;
	@NonNull private String country;
	@NonNull private String po;
	
}
