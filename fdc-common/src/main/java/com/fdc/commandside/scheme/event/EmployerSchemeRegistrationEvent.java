package com.fdc.commandside.scheme.event;

import com.fdc.common.event.StatefulAbstractEvent;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.scheme.model.AgreementModel;
import com.fdc.common.scheme.model.EmployerModel;
import com.fdc.common.scheme.model.SchemeModel;
import com.fdc.common.util.EventType;
import com.fdc.common.util.SchemeStatusType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("EmployerSchemeRegistrationEvent")
public class EmployerSchemeRegistrationEvent extends StatefulAbstractEvent {

	private static final long serialVersionUID = -5844694922012659814L;

	private SchemeModel schemeModel;
	private AgreementModel agreementModel;
	private EmployerModel employerModel;
	
	public EmployerSchemeRegistrationEvent(String id, AuditEntry auditEntry, SchemeModel schemeModel,
			AgreementModel agreementModel, EmployerModel employerModel) {
		super(id, EventType.DOMAIN_EVENT, auditEntry,SchemeStatusType.CREATED.getValue());
		this.schemeModel = schemeModel;
		this.agreementModel = agreementModel;
		this.employerModel = employerModel;
	}

	public SchemeModel getSchemeModel() {
		return schemeModel;
	}

	public AgreementModel getAgreementModel() {
		return agreementModel;
	}

	public EmployerModel getEmployerModel() {
		return employerModel;
	}

	
	
	
}
