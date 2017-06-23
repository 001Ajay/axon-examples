package com.fdc.common.customer.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class TaxModel {

	@NonNull private String taxNumber;
	@NonNull private String taxCardType;
	
}
