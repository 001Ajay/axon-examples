package com.fdc.commandside.scheme;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdc.commandside.scheme.web.RegisterEmployerRequest;
import com.fdc.common.model.AuditEntry;
import com.fdc.common.scheme.model.AgreementModel;
import com.fdc.common.scheme.model.EmployerModel;
import com.fdc.common.scheme.model.SchemeModel;
import com.fdc.common.util.AgreementType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SchemeCommandServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Test
	public void registerEmployerStart() throws Exception{
		//AuditEntry auditEntry = new AuditEntry("Rana", Calendar.getInstance().getTime());
		AuditEntry auditEntry = new AuditEntry("Rana1323");
		SchemeModel schemeData = new SchemeModel("Scheme1","SchemeCode1");
		List<String> productList = new ArrayList<String>();
		productList.add("Critical Illness");
		AgreementModel agreementData = new AgreementModel("OVK1",AgreementType.OVK,productList);
		EmployerModel employerData = new EmployerModel("Prudential","66666666");
		RegisterEmployerRequest registerEmployerRequest = new RegisterEmployerRequest(schemeData,agreementData,employerData);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(registerEmployerRequest);
		LOG.debug("JSON request string -" + jsonInString);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/registerEmployer",registerEmployerRequest, String.class);
		LOG.debug("Response status from registerEmployer = " + responseEntity.getStatusCode());
		String schemeId = responseEntity.getBody();
		LOG.debug("Scheme ID = " + schemeId);
		assertNotNull(schemeId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
}
