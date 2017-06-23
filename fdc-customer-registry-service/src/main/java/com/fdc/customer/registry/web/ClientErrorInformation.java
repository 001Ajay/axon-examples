package com.fdc.customer.registry.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientErrorInformation {

	private String errorMessage;
	private String requestURI;
}
