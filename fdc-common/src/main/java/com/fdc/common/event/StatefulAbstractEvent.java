package com.fdc.common.event;

import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.EventType;

/**
 * A base, domain event class.
 * 
 * @author rana
 *
 */
public abstract class StatefulAbstractEvent extends AuditableAbstractEvent {

	private static final long serialVersionUID = 389787334026753874L;

	private String status;
	
	public StatefulAbstractEvent() {

    }

    public StatefulAbstractEvent(String id, EventType type, AuditEntry auditEntry, String status) {
        super(id,type,auditEntry);
        this.status = status;
    }

	public String getStatus() {
		return status;
	}

    
  
}
