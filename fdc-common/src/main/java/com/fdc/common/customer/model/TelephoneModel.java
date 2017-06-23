package com.fdc.common.customer.model;

import com.fdc.common.util.TelephoneType;

import lombok.Data;
import lombok.NonNull;

@Data
public class TelephoneModel {

	private String countryCode;
	@NonNull private String number;
	@NonNull private TelephoneType type;
}
