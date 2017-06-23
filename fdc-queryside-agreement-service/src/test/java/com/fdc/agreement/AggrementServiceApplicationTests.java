package com.fdc.agreement;

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

import com.fdc.agreement.web.EmployerAgreementResource;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AggrementServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	//@Test
	public void loadAgreementProductData() throws Exception{

	}
	
	@Test
	public void getAllEmployerArgeements() throws Exception{
			
		final String url = "/employer/agreements";
	    
		ResponseEntity<?> responseEntity = restTemplate.getForEntity(url,
				ArrayList.class);
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		LOG.debug("Response " + responseEntity.getBody().toString());
	}
	
	@Test
	public void searchEmployerAgreementByCVR() throws Exception{
			
		final String url = "/employer/agreements/{cvr}";
		Map<String, String> params = new HashMap<String, String>();
	    params.put("cvr", "4500");
	    
		//ResponseEntity<EmployerAgreementResource> responseEntity = restTemplate.getForEntity(url,
			//	EmployerAgreementResource.class,params);
	    
	    ResponseEntity<?> responseEntity = restTemplate.getForEntity(url,
				ArrayList.class);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(((EmployerAgreementResource)responseEntity.getBody()).getCvr(), "4500");
		LOG.debug("Response " + responseEntity.getBody().toString());
	}

}
