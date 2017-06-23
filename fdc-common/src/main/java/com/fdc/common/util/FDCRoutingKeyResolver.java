package com.fdc.common.util;

import org.axonframework.amqp.eventhandling.RoutingKeyResolver;
import org.axonframework.eventhandling.EventMessage;

import com.fdc.common.event.AbstractEvent;

public class FDCRoutingKeyResolver implements RoutingKeyResolver{

	@Override
	public String resolveRoutingKey(EventMessage<?> eventMessage) {
		
		if(AbstractEvent.class.isInstance(eventMessage.getPayload())){
			return ((AbstractEvent)eventMessage.getPayload()).getType().getValue();
		}
		
		return eventMessage.getPayloadType().getPackage().getName();
	}

	
}
