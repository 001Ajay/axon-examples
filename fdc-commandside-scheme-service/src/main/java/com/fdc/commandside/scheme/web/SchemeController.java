package com.fdc.commandside.scheme.web;

import java.security.Principal;

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
import org.springframework.web.client.RestTemplate;

import com.fdc.commandside.agreement.command.StartEmployerAgreementCreationCommand;
import com.fdc.commandside.scheme.command.RegisterEmployerCommand;
import com.fdc.common.model.AuditEntry;

@RestController
public class SchemeController {

	@Autowired
    CommandGateway commandGateway;
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/registerEmployer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody RegisterEmployerRequest request,Principal principal) {
        LOG.debug(RegisterEmployerRequest.class.getSimpleName() + " request received");

        AuditEntry auditEntry = new AuditEntry("Rana1");
        RegisterEmployerCommand command = new RegisterEmployerCommand(auditEntry,request.getSchemeData(),
        		request.getAgreementData(),request.getEmployerData());
        commandGateway.sendAndWait(command);
        
        LOG.debug("Command processed with Scheme ID [{}] ", command.getSchemeId());
        
        return new ResponseEntity<String>(command.getSchemeId(),HttpStatus.CREATED);
    }
}
