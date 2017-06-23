package com.fdc.commandside.customer.integration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fdc.common.customer.model.EmailAddressModel;
import com.fdc.common.customer.model.MailAddressModel;
import com.fdc.common.customer.model.TaxModel;
import com.fdc.common.customer.model.TelephoneModel;
import com.fdc.common.util.AddressUsageType;
import com.fdc.common.util.CommunicationUsageType;
import com.fdc.common.util.EmailType;
import com.fdc.common.util.TelephoneType;

@Component
public class CustomerRegistryClientFallbackImpl implements CustomerRegistryFeignClient {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Override
	public CustomerResource getCustomerFromRegistryByNationalId(String nationalId) {

		CustomerResource cust = new CustomerResource(nationalId);
		//setting dummy id and name
		cust.setCustomerId("111111");
		cust.setCompanyName("Dummy company");
		// dummy code
		/*Map<AddressUsageType, MailAddressModel> addresses = new HashMap<AddressUsageType, MailAddressModel>();
		addresses.put(AddressUsageType.PERMANENT,
				new MailAddressModel("Addline 1", "City1", "State1", "Country1", "PO1"));
		cust.setAddresses(addresses);

		Map<CommunicationUsageType, TelephoneModel> telephones = new HashMap<CommunicationUsageType, TelephoneModel>();
		telephones.put(CommunicationUsageType.WORK, new TelephoneModel("Number1", TelephoneType.LANDLINE));
		cust.setTelephones(telephones);

		Map<CommunicationUsageType, EmailAddressModel> emails = new HashMap<CommunicationUsageType, EmailAddressModel>();
		emails.put(CommunicationUsageType.WORK, new EmailAddressModel("a@c.com", EmailType.CORPORATE));
		cust.setEmails(emails);

		TaxModel taxInfo = new TaxModel("TaxNumber1", "SKAT");
		cust.setTaxInfo(taxInfo);*/

		LOG.debug("Response from fallback agreement product service = " + cust.toString());
		return cust;
	}

}
