package com.fdc.common.event;

import java.io.Serializable;

import com.fdc.common.util.EventType;

/**
 * A base abstract event class.
 * 
 * @author rana
 *
 */
public abstract class AbstractEvent implements Serializable {

    private static final long serialVersionUID = -6658015030606619450L;

    private String id;
    private EventType type;

    public AbstractEvent() {

    }

    public AbstractEvent(String id,EventType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

	public EventType getType() {
		return type;
	}
    
    
}