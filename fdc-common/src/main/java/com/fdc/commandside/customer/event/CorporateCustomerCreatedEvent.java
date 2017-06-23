package com.fdc.commandside.customer.event;

import com.fdc.common.customer.model.CorporateCustomerModel;
import com.fdc.common.event.StatefulAbstractEvent;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.CustomerStatusType;
import com.fdc.common.util.EventType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

@XStreamAlias("CorporateCustomerCreatedEvent")
@Data
public class CorporateCustomerCreatedEvent extends StatefulAbstractEvent {

	private static final long serialVersionUID = -5844694922012659814L;
	
	private String employerId;
	private CorporateCustomerModel corporateCustomer;
	private String agreementId;
	
	public CorporateCustomerCreatedEvent(String id, AuditEntry auditEntry, CorporateCustomerModel corporateCustomer,String agreementId) {
		super(id, EventType.DOMAIN_EVENT, auditEntry,CustomerStatusType.CREATED.getValue());
		this.corporateCustomer = corporateCustomer;
		this.employerId = id;
		this.agreementId = agreementId;
	}

}
