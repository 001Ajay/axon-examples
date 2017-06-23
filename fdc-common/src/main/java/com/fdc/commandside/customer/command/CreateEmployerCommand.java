package com.fdc.commandside.customer.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.fdc.common.command.AuditableAbstractCommand;
import com.fdc.common.customer.model.CorporateCustomerModel;
import com.fdc.common.model.AuditEntry;

import lombok.Data;

@Data
public class CreateEmployerCommand extends AuditableAbstractCommand{

	@TargetAggregateIdentifier
	private String customerId;
	private CorporateCustomerModel employerModel;

	public CreateEmployerCommand(AuditEntry auditEntry,String employerId,CorporateCustomerModel employerModel) {
		this.customerId = employerId;
		this.employerModel = employerModel;
		setAuditEntry(auditEntry);
	}

}
