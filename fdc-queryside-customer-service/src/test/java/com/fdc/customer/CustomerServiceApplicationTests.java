package com.fdc.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdc.common.customer.model.EmailAddressModel;
import com.fdc.common.customer.model.MailAddressModel;
import com.fdc.common.customer.model.TaxModel;
import com.fdc.common.customer.model.TelephoneModel;
import com.fdc.common.util.AddressUsageType;
import com.fdc.common.util.CommunicationUsageType;
import com.fdc.common.util.EmailType;
import com.fdc.common.util.TelephoneType;
import com.fdc.customer.aggregate.CorporateCustomer;
import com.fdc.customer.web.CorporateCustomerResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void loadCustomerData() throws Exception{
		CorporateCustomer cust = new CorporateCustomer("123456", "AP Pension", "8888-9999");
		
		Map<AddressUsageType,MailAddressModel> addresses = new HashMap<AddressUsageType,MailAddressModel>();
		addresses.put(AddressUsageType.PERMANENT, new MailAddressModel("Addline 1","City1","State1","Country1","PO1"));
		cust.setAddresses(addresses);
		
		Map<CommunicationUsageType,TelephoneModel> telephones = new HashMap<CommunicationUsageType,TelephoneModel>();
		telephones.put(CommunicationUsageType.WORK, new TelephoneModel("Number1",TelephoneType.LANDLINE));
		cust.setTelephones(telephones);
		
		Map<CommunicationUsageType,EmailAddressModel> emails = new HashMap<CommunicationUsageType,EmailAddressModel>();
		emails.put(CommunicationUsageType.WORK, new EmailAddressModel("a@c.com",EmailType.CORPORATE));
		cust.setEmails(emails);
		
		TaxModel taxInfo = new TaxModel("TaxNumber1","SKAT");
		cust.setTaxInfo(taxInfo);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(cust);
		LOG.debug("JSON request string -" + jsonInString);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://fdc-queryside-customer-service.cfapps.io/corporate/customers",cust, String.class);
		LOG.debug("Response status from create customer = " + responseEntity.getStatusCode());
		String customerId = responseEntity.getBody();
		LOG.debug("Customer ID = " + customerId);
		assertNotNull(customerId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		cust = new CorporateCustomer("345678", "Pension Denmark", "7777-9999");
		
		addresses = new HashMap<AddressUsageType,MailAddressModel>();
		addresses.put(AddressUsageType.PERMANENT, new MailAddressModel("Addline 1","City2","State2","Country1","PO2"));
		cust.setAddresses(addresses);
		
		telephones = new HashMap<CommunicationUsageType,TelephoneModel>();
		telephones.put(CommunicationUsageType.WORK, new TelephoneModel("Number2",TelephoneType.LANDLINE));
		cust.setTelephones(telephones);
		
		emails = new HashMap<CommunicationUsageType,EmailAddressModel>();
		emails.put(CommunicationUsageType.WORK, new EmailAddressModel("a@x.com",EmailType.CORPORATE));
		cust.setEmails(emails);
		
		taxInfo = new TaxModel("TaxNumber2","SKAT");
		cust.setTaxInfo(taxInfo);
		
		mapper = new ObjectMapper();
		jsonInString = mapper.writeValueAsString(cust);
		LOG.debug("JSON request string -" + jsonInString);
		
		responseEntity = restTemplate.postForEntity("https://fdc-queryside-customer-service.cfapps.io/corporate/customers",cust, String.class);
		LOG.debug("Response status from create customer = " + responseEntity.getStatusCode());
		customerId = responseEntity.getBody();
		LOG.debug("Customer ID = " + customerId);
		assertNotNull(customerId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
	}
	
	//@Test
	public void getAllCorporateCustomers() throws Exception{
			
		final String url = "/corporate/customers";
	    
		ResponseEntity<?> responseEntity = restTemplate.getForEntity(url,
				ArrayList.class);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		LOG.debug("Response " + responseEntity.getBody().toString());
	}
	
	//@Test
	public void getCorporateCustomerById() throws Exception{
			
		final String url = "/corporate/customers/{id}";
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", "123456");
	    
		ResponseEntity<CorporateCustomerResource> responseEntity = restTemplate.getForEntity(url,
				CorporateCustomerResource.class,params);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody().getCustomerId(), "123456");
		LOG.debug("Response from getCorporateCustomerById - " + responseEntity.getBody().toString());
	}
	
	//@Test
	public void searchCorporateCustomerByCVR() throws Exception{
			
		final String url = "/corporate/customers/searchByCustomerCVR?cvr={cvr}";
	    
		ResponseEntity<CorporateCustomerResource> responseEntity = restTemplate.getForEntity(url,
				CorporateCustomerResource.class,"8888-9999");
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody().getCvr(), "8888-9999");
		LOG.debug("Response from searchCorporateCustomerByCVR - " + responseEntity.getBody().toString());
	}
	
	//@Test
	public void searchCorporateCustomerByName() throws Exception{
			
		final String url = "/corporate/customers/searchByCustomerName?name={name}";
	    
		ResponseEntity<CorporateCustomerResource> responseEntity = restTemplate.getForEntity(url,
				CorporateCustomerResource.class,"AP Pension");
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody().getCompanyName(), "AP Pension");
		LOG.debug("Response from searchCorporateCustomerByName - " + responseEntity.getBody().toString());
	}

}
