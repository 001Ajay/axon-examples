package com.fdc.common.customer.model;

import java.util.Map;

import com.fdc.common.util.AddressUsageType;
import com.fdc.common.util.CommunicationUsageType;

import lombok.Data;
import lombok.NonNull;

@Data
public class IndividualCustomerModel {

	@NonNull private String cpr;
	@NonNull private String name;
	private Map<AddressUsageType,MailAddressModel> addresses;
	private Map<CommunicationUsageType,TelephoneModel> telephones;
	private Map<CommunicationUsageType,EmailAddressModel> emails;
	private TaxModel taxInfo;
	
}
