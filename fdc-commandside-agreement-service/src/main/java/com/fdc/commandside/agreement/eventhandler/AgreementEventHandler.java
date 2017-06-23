package com.fdc.commandside.agreement.eventhandler;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdc.common.exchange.newbusiness.AssociateEmployerWithSchemeCommand;
import com.fdc.common.exchange.newbusiness.EmployerCreatedEvent;
import com.fdc.common.exchange.newbusiness.EmployerToBeCreatedEvent;

//@ProcessingGroup("customerExchangeEventProcessor")
@Component
public class AgreementEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AgreementEventHandler.class);

	@Autowired
	private CommandGateway commandGateway;
	
	@EventHandler
	public void handle(EmployerCreatedEvent event) {
		LOG.info("EmployerCreatedEvent processing for corporate customer ID {} and Scheme Id {} ", event.getId(),event.getSchemeId());
		// convert to a command and fire
		AssociateEmployerWithSchemeCommand command = new AssociateEmployerWithSchemeCommand(event.getAuditEntry(), 
				event.getId(), event.getSchemeId());
		
		commandGateway.sendAndWait(command);
	}
}
