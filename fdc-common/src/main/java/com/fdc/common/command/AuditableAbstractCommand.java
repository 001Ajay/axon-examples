package com.fdc.common.command;

import com.fdc.common.model.AuditEntry;

import lombok.Data;
/**
 * A base class for all commands that want to have {@link AuditEntry} included
 * 
 * @author rana
 *
 */
@Data
public abstract class AuditableAbstractCommand {

    private AuditEntry auditEntry;

}
