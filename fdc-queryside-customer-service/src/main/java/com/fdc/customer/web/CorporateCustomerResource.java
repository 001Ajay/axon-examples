package com.fdc.customer.web;

import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

import com.fdc.common.customer.model.EmailAddressModel;
import com.fdc.common.customer.model.MailAddressModel;
import com.fdc.common.customer.model.TaxModel;
import com.fdc.common.customer.model.TelephoneModel;
import com.fdc.common.util.AddressUsageType;
import com.fdc.common.util.CommunicationUsageType;

import lombok.Data;
import lombok.NonNull;

@Data
public class CorporateCustomerResource extends ResourceSupport{

	@NonNull private String customerId;
	@NonNull private String companyName;
	@NonNull private String cvr;
	private Map<AddressUsageType,MailAddressModel> addresses;
	private Map<CommunicationUsageType,TelephoneModel> telephones;
	private Map<CommunicationUsageType,EmailAddressModel> emails;
	private TaxModel taxInfo;


}
