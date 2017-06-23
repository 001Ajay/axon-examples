package com.fdc.commandside.agreement.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fdc.commandside.agreement.command.StartEmployerAgreementCreationCommand;
import com.fdc.commandside.agreement.integration.AgreementProductClient;
import com.fdc.commandside.agreement.integration.AgreementProductResource;
import com.fdc.common.agreement.model.AgreementModel;
import com.fdc.common.model.AuditEntry;

@RestController
public class AgreementController {

	@Autowired
    CommandGateway commandGateway;
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/agreements", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody CreateAgreementRequest request,Principal principal) {
        LOG.info(CreateAgreementRequest.class.getSimpleName() + " request received");

        AuditEntry auditEntry = new AuditEntry("Rana1");
        
        StartEmployerAgreementCreationCommand command = new StartEmployerAgreementCreationCommand(request.getCvr(),
        													request.getName(),request.getPnumber(),
        													request.getOvkCodes(),auditEntry);
        commandGateway.sendAndWait(command);
        
        LOG.info("Command processed " + command.toString());
        
        return new ResponseEntity<String>(command.getAgreementId(),HttpStatus.CREATED);
    }

	
}
