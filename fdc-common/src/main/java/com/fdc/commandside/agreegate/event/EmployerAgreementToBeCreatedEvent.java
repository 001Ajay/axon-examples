package com.fdc.commandside.agreegate.event;

import java.util.List;

import com.fdc.common.agreement.model.AgreementModel;
import com.fdc.common.agreement.model.CustomerModel;
import com.fdc.common.event.StatefulAbstractEvent;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.util.AgreementType;
import com.fdc.common.util.EventType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;
import lombok.NonNull;

@XStreamAlias("agreementToBeCreatedEvent")
@Data
public class EmployerAgreementToBeCreatedEvent extends StatefulAbstractEvent {

	private static final long serialVersionUID = -5844694922012659814L;
	
	@NonNull private String cvr;
	@NonNull private String pnumber;
	@XStreamImplicit
	@NonNull private List<AgreementModel> agreementModels;
	@NonNull private CustomerModel customer;
	
	public EmployerAgreementToBeCreatedEvent(String id,String cvr, String pnumber, List<AgreementModel> agreementModels,
			CustomerModel customer,AuditEntry auditEntry) {
		super(id, EventType.DOMAIN_EVENT, auditEntry,AgreementType.OVK.getValue());
		this.cvr = cvr;
		this.pnumber = pnumber;
		this.agreementModels = agreementModels;
		this.customer = customer;
	}
	
}
