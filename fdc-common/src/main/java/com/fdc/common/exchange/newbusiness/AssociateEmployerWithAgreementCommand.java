package com.fdc.common.exchange.newbusiness;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.fdc.common.command.AuditableAbstractCommand;
import com.fdc.common.model.AuditEntry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssociateEmployerWithAgreementCommand extends AuditableAbstractCommand{

	@TargetAggregateIdentifier
	private String employerId;
	private String agreementId;
	
}
