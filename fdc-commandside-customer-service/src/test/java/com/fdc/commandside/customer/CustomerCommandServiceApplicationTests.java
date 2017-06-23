package com.fdc.commandside.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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

import com.fdc.commandside.customer.web.CreateCorporateCustomerRequest;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.scheme.model.AgreementModel;
import com.fdc.common.scheme.model.EmployerModel;
import com.fdc.common.scheme.model.SchemeModel;
import com.fdc.common.util.AgreementType;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerCommandServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Test
	public void createCorporateCustomer() {
		//AuditEntry auditEntry = new AuditEntry("Rana", Calendar.getInstance().getTime());
		AuditEntry auditEntry = new AuditEntry("Rana1");
	
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/createCorporateCustomer",
				new CreateCorporateCustomerRequest("AP Pension","8888-9999"), String.class);
		LOG.debug("Response status from registerEmployer = " + responseEntity.getStatusCode());
		String customerId = responseEntity.getBody();
		LOG.debug("Customer ID = " + customerId);
		assertNotNull(customerId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
}
