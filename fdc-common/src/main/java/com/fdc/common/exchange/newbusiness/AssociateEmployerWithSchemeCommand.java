package com.fdc.common.exchange.newbusiness;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.fdc.common.command.AuditableAbstractCommand;
import com.fdc.common.model.AuditEntry;

import lombok.Data;

@Data
public class AssociateEmployerWithSchemeCommand extends AuditableAbstractCommand{

	@TargetAggregateIdentifier
	private String employerId;
	private String schemeId;
	
	public AssociateEmployerWithSchemeCommand(AuditEntry auditEntry,String employerId,String schemeId) {
		this.employerId = employerId;
		this.schemeId = schemeId;
		setAuditEntry(auditEntry);
	}

}
