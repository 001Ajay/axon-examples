package com.fdc.common.exchange.newbusiness;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.fdc.common.command.AuditableAbstractCommand;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateEmployerAsCustomerCommand extends AuditableAbstractCommand{

	@TargetAggregateIdentifier
	private String employerId;
	private String cvr;
	private String name;
	private String agreementId;
}
