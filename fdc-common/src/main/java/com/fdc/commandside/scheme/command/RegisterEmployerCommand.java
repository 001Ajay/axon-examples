package com.fdc.commandside.scheme.command;

import java.util.UUID;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.fdc.common.command.AuditableAbstractCommand;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.scheme.model.AgreementModel;
import com.fdc.common.scheme.model.EmployerModel;
import com.fdc.common.scheme.model.SchemeModel;

import lombok.Data;

@Data
public class RegisterEmployerCommand extends AuditableAbstractCommand{

	@TargetAggregateIdentifier
	private String schemeId;
	private SchemeModel schemeModel;
	private AgreementModel agreementModel;
	private EmployerModel employerModel;
	
	public RegisterEmployerCommand(AuditEntry auditEntry, SchemeModel schemeModel, AgreementModel agreementModel,
			EmployerModel employerModel) {
		this.schemeId = UUID.randomUUID().toString();
		this.schemeModel = schemeModel;
		this.agreementModel = agreementModel;
		this.employerModel = employerModel;
		setAuditEntry(auditEntry);
	}

}
