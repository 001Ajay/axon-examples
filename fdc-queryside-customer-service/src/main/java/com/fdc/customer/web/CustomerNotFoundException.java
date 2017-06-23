package com.fdc.customer.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = 100L;
	
	CustomerNotFoundException(String reason){
		super(reason);
	}
	
}
