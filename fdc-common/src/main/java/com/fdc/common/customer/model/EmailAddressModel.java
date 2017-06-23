package com.fdc.common.customer.model;

import com.fdc.common.util.EmailType;

import lombok.Data;
import lombok.NonNull;

@Data
public class EmailAddressModel {

	@NonNull private String emailAddress;
	@NonNull private EmailType type;
}
