package com.fdc.commandside.scheme.event;

import com.fdc.common.event.StatefulAbstractEvent;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.EventType;
import com.fdc.common.util.SchemeStatusType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("EmployerSchemePublishedEvent")
public class EmployerSchemePublishedEvent extends StatefulAbstractEvent {
	private static final long serialVersionUID = -8854684024658085020L;
	
	private String employerId;
	
	public EmployerSchemePublishedEvent(AuditEntry auditEntry,String schemeId,String employreId) {
		super(schemeId, EventType.DOMAIN_EVENT, auditEntry,SchemeStatusType.PUBLISHED.getValue());
		this.employerId = employreId;
	}

	public String getEmployerId() {
		return employerId;
	}
	
	
}
