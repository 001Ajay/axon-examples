package com.fdc.common.customer.model;

import java.util.Map;

import com.fdc.common.util.AddressUsageType;
import com.fdc.common.util.CommunicationUsageType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CorporateCustomerModel {

	private String companyId;
	private String companyName;
	private String cvr;
	private Map<AddressUsageType,MailAddressModel> addresses;
	private Map<CommunicationUsageType,TelephoneModel> telephones;
	private Map<CommunicationUsageType,EmailAddressModel> emails;
	private TaxModel taxInfo;
}
