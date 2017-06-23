package com.fdc.agreementProduct.aggregate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgreementProductNotFoundException extends Exception {

	private static final long serialVersionUID = 100L;
	
	AgreementProductNotFoundException(String reason){
		super(reason);
	}
	
}
