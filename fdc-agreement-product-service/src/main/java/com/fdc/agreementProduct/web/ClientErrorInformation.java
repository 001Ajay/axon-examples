package com.fdc.agreementProduct.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientErrorInformation {

	private String errorMessage;
	private String requestURI;
}
