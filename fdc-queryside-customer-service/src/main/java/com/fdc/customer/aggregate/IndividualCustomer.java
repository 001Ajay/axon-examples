package com.fdc.customer.aggregate;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fdc.common.customer.model.EmailAddressModel;
import com.fdc.common.customer.model.MailAddressModel;
import com.fdc.common.customer.model.TaxModel;
import com.fdc.common.customer.model.TelephoneModel;
import com.fdc.common.util.AddressUsageType;
import com.fdc.common.util.CommunicationUsageType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class IndividualCustomer {

	private static final Logger LOG = LoggerFactory.getLogger(IndividualCustomer.class);

	@NonNull private String customerId;
	@NonNull private String cpr;
	@NonNull private String name;
	private Map<AddressUsageType,MailAddressModel> addresses;
	private Map<CommunicationUsageType,TelephoneModel> telephones;
	private Map<CommunicationUsageType,EmailAddressModel> emails;
	private TaxModel taxInfo;


}