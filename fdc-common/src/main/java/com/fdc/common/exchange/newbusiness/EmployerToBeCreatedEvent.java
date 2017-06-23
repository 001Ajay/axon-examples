package com.fdc.common.exchange.newbusiness;

import com.fdc.common.event.AuditableAbstractEvent;
import com.fdc.common.exchange.newbusiness.model.CustomerModel;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.EventType;


public class EmployerToBeCreatedEvent extends AuditableAbstractEvent{

	private static final long serialVersionUID = 874392853870483892L;
	
	private CustomerModel employer;
	
	public EmployerToBeCreatedEvent(AuditEntry auditEntry, String schemeId, CustomerModel employer) {
		super(schemeId, EventType.EXCHANGE_EVENT, auditEntry);
		this.employer = employer;
	}

	public CustomerModel getEmployer() {
		return employer;
	}

}
