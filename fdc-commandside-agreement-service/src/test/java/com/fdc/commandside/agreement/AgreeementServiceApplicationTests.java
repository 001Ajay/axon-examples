package com.fdc.commandside.agreement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.fdc.commandside.agreement.web.CreateAgreementRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AgreeementServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Test
	public void registerEmployerStart() throws Exception{

		List<String> ovkList = new ArrayList<String>();
		ovkList.add("4500");
		//ovkList.add("3647");
		
		CreateAgreementRequest registerEmployerRequest = new CreateAgreementRequest("1234-1234","AP Pension","12345678901",ovkList);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(registerEmployerRequest);
		LOG.debug("JSON request string -" + jsonInString);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://demo-agreement-service.cfapps.io/createAgreement",registerEmployerRequest, String.class);
		LOG.debug("Response status from registerEmployer = " + responseEntity.getStatusCode());
		String agreementId = responseEntity.getBody();
		LOG.debug("Agreement ID = " + agreementId);
		assertNotNull(agreementId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
}
