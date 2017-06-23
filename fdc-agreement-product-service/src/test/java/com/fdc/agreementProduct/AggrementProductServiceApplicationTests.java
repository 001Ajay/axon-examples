package com.fdc.agreementProduct;

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
import com.fdc.agreementProduct.web.CreateAgreementProductRequest;
import com.fdc.agreementProduct.web.AgreementProductResource;
import com.fdc.common.agreementProduct.model.PolicyProductModel;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AggrementProductServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void loadAgreementProductData() throws Exception{
		CreateAgreementProductRequest request = new CreateAgreementProductRequest("302","3100", "Standard med sundhed og SUBA", "agreementProrType1");
		List<PolicyProductModel> policyProducts = new ArrayList<PolicyProductModel>();
		
		policyProducts.add(new PolicyProductModel("100","Standard med sundhedsordning", "AMP1"));
		policyProducts.add(new PolicyProductModel("200","Supplerende ordn. via arbejdsgiver", "SUBA"));
		
		request.setPolicyProducts(policyProducts);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(request);
		LOG.debug("JSON request string -" + jsonInString);
		
		//ResponseEntity<String> responseEntity = restTemplate.postForEntity("/agreementProducts",request, String.class);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://fdc-agreement-product-service.cfapps.io/agreementProducts",request, String.class);
		LOG.debug("Response status from createAgreementProduct = " + responseEntity.getStatusCode());
		String agreementProductCode = responseEntity.getBody();
		LOG.debug("Agreement ID = " + agreementProductCode);
		assertNotNull(agreementProductCode);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	//@Test
	public void searchAgreementProductByOVK() throws Exception{
			
		final String url = "/agreementProducts/searchByOVKCode?ovkCode={ovkCode}";
	    
		ResponseEntity<AgreementProductResource> responseEntity = restTemplate.getForEntity(url, 
			    AgreementProductResource.class, "3100");
		
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		LOG.debug("Response " + responseEntity.getBody().toString());
		assertEquals(responseEntity.getBody().getOvkAgreementCode(),"3100");
	}

}
