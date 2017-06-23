package com.fdc.agreement.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgreementNotFoundException extends Exception {

	private static final long serialVersionUID = 100L;
	
	AgreementNotFoundException(String reason){
		super(reason);
	}
	
}
