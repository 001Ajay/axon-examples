package com.fdc.commandside.agreegate.event;

import com.fdc.common.agreement.model.CustomerModel;
import com.fdc.common.event.StatefulAbstractEvent;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.AgreementType;
import com.fdc.common.util.EventType;
import com.fdc.common.util.SchemeStatusType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;
import lombok.NonNull;

@XStreamAlias("agreementCreatedEvent")
@Data
public class EmployerAgreementCreatedEvent extends StatefulAbstractEvent {
	private static final long serialVersionUID = -8854684024658085020L;
	
	@NonNull private CustomerModel employer;
	
	public EmployerAgreementCreatedEvent(AuditEntry auditEntry,String agreementId,CustomerModel employer) {
		super(agreementId, EventType.DOMAIN_EVENT, auditEntry,AgreementType.OVK.getValue());
		this.employer = employer;
	}
	
}
