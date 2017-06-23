package com.fdc.commandside.agreement.command;

import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.fdc.common.agreement.model.AgreementModel;
import com.fdc.common.command.AuditableAbstractCommand;
import com.fdc.common.model.AuditEntry;

import lombok.Data;
import lombok.NonNull;

@Data
public class StartEmployerAgreementCreationCommand extends AuditableAbstractCommand{

	@TargetAggregateIdentifier
	private String agreementId;
	@NonNull private String cvr;
	@NonNull private String name;
	@NonNull private String pnumber;
	@NonNull private List<String> ovks;
	
	public StartEmployerAgreementCreationCommand(String cvr, String name, String pnumber, List<String> ovks, AuditEntry auditEntry) {
		this.agreementId = UUID.randomUUID().toString();
		this.cvr = cvr;
		this.name = name;
		this.pnumber = pnumber;
		this.ovks = ovks;
		setAuditEntry(auditEntry);
	}
}
