package com.fdc.customer.registry.aggregate;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fdc.common.customer.model.EmailAddressModel;
import com.fdc.common.customer.model.MailAddressModel;
import com.fdc.common.customer.model.TaxModel;
import com.fdc.common.customer.model.TelephoneModel;
import com.fdc.common.util.AddressUsageType;
import com.fdc.common.util.CommunicationUsageType;

import lombok.Data;
import lombok.NonNull;

@Data
@Document(collection = "customerRegistry")
public class Customer {

	private static final Logger LOG = LoggerFactory.getLogger(Customer.class);
	
	@NonNull @Id private String customerId;
	@NonNull @Indexed private String name;
	@NonNull @Indexed private String nationalId;
	private Map<AddressUsageType,MailAddressModel> addresses;
	private Map<CommunicationUsageType,TelephoneModel> telephones;
	private Map<CommunicationUsageType,EmailAddressModel> emails;
	private TaxModel taxInfo;

}