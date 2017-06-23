package com.fdc.common.exchange.newbusiness;

import com.fdc.common.event.AuditableAbstractEvent;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.EventType;


public class EmployerCreatedEvent extends AuditableAbstractEvent{

	private static final long serialVersionUID = 8534576069091773231L;

	private String schemeId;
	
	public EmployerCreatedEvent(AuditEntry auditEntry, String employerId,String schemeId) {
		super(employerId, EventType.EXCHANGE_EVENT, auditEntry);
		this.schemeId = schemeId;
	}

	public String getSchemeId() {
		return schemeId;
	}
	
	

}
