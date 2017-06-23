package com.fdc.commandside.scheme.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fdc.commandside.agreement.command.StartEmployerAgreementCreationCommand;
import com.fdc.commandside.scheme.command.RegisterEmployerCommand;
import com.fdc.common.exchange.newbusiness.AssociateEmployerWithSchemeCommand;
import com.fdc.common.exchange.newbusiness.CreateEmployerAsCustomerCommand;


@Component
public class SchemeCommandHandler {

	private static final Logger LOG = LoggerFactory.getLogger(SchemeCommandHandler.class);
	
    private Repository<Scheme> repository;
    
    @Autowired
    EventBus eventBus;
    
    @Autowired
    @Qualifier("schemeRepository")
    public void setRepository(Repository<Scheme> repository) {
        this.repository = repository;
    }
	
	@CommandHandler
	public void handleRegisterEmployer(RegisterEmployerCommand command) throws Exception
	{
		//Command processing logic
		LOG.debug("Scheme id = " + command.getSchemeId());
		
		//Store the domain event in ES
		//Aggregate<Scheme> scheme = 
		repository.newInstance(() -> {
            return new Scheme(command.getAuditEntry(),
            		command.getSchemeId(),
            		command.getSchemeModel(),
            		command.getAgreementModel(),
            		command.getEmployerModel());
        });
	}
	
	/*@CommandHandler
	public void handleCreateEmployerAsCustomer(CreateEmployerAsCustomerCommand command) throws Exception
	{
		Aggregate<Scheme> scheme = repository.load(command.getSchemeId());
		scheme.execute(aggregateRoot ->{
			aggregateRoot.createCustomerForEmployer(command.getAuditEntry(), command.getEmployerId(),command.getEmployerModel(),command.getSchemeId());
		});
	}*/
	

	@CommandHandler
	public void handleAssociateEmployerWithScheme(AssociateEmployerWithSchemeCommand command) throws Exception
	{
		Aggregate<Scheme> scheme = repository.load(command.getSchemeId());
		scheme.execute(aggregateRoot ->{
			aggregateRoot.associateEmployerWithScheme(command.getAuditEntry(),command.getSchemeId(),command.getEmployerId());
		});
	}
}