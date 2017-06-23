package com.fdc.common.event;

import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.EventType;

/**
 * A base, auditable event class.
 * 
 * @author rana
 *
 */
public abstract class AuditableAbstractEvent extends AbstractEvent {

    private static final long serialVersionUID = -5389550139760061559L;

    private AuditEntry auditEntry;

    public AuditableAbstractEvent() {

    }

    public AuditableAbstractEvent(String id, EventType type, AuditEntry auditEntry) {
        super(id,type);
        this.setAuditEntry(auditEntry);
    }

    public AuditEntry getAuditEntry() {
        return auditEntry;
    }

    public void setAuditEntry(AuditEntry auditEntry) {
        this.auditEntry = auditEntry;
    }
    
    

}
