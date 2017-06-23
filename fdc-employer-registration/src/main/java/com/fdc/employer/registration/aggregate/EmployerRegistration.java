package com.fdc.employer.registration.aggregate;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AggregateRoot
public class EmployerRegistration {

	private static final Logger LOG = LoggerFactory.getLogger(EmployerRegistration.class);

	@AggregateIdentifier
	private String registrationId;
	private String cvrNumber;
	private String employerName;

}