package com.fdc.agreementProduct.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdc.agreementProduct.aggregate.AgreementProduct;
import com.fdc.agreementProduct.aggregate.AgreementProductNotFoundException;
import com.fdc.agreementProduct.aggregate.AgreementProductService;

@RestController
@RequestMapping(value="/agreementProducts")
public class AgreementProductController {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AgreementProductService service;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody CreateAgreementProductRequest request,Principal principal) {
        LOG.debug(CreateAgreementProductRequest.class.getSimpleName() + " request received");
        
        String agreementProductNumber = service.saveAgreementProduct(request.getOvkAgreementNumber(),
        		request.getOvkAgreementCode(), request.getAgreementProductName(), request.getAgreementProductType(),request.getPolicyProducts());
        
        LOG.debug("AgreementProduct is saved with number [{}]",agreementProductNumber);
        
        return new ResponseEntity<String>(agreementProductNumber,HttpStatus.CREATED);
    }
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAgreementProducts() throws AgreementProductNotFoundException{
        LOG.debug( "Request for findAgreementProducts received ");

     
        List<AgreementProduct> aps = service.getAgreementProducts();
        List<AgreementProductResource> resources = new ArrayList<AgreementProductResource>();
        
        AgreementProductResourceAssembler corporateCustomerResourceAssembler = new AgreementProductResourceAssembler(AgreementProductController.class, 
        		AgreementProductResource.class);
        AgreementProductResource resource = null;
        for(AgreementProduct ap:aps){
        	resource = corporateCustomerResourceAssembler.toResource(ap);
        	resources.add(resource);
        }

        return new ResponseEntity<>(resources,HttpStatus.OK);
        
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findAgreementProductById(@PathVariable String id) throws AgreementProductNotFoundException{
        LOG.debug( "Request for findAgreementProductById received with id [{}]",id);

     
        AgreementProduct ap = service.getAgreementProductById(id);
        AgreementProductResourceAssembler corporateCustomerResourceAssembler = new AgreementProductResourceAssembler(AgreementProductController.class, 
        		AgreementProductResource.class);
        AgreementProductResource response = corporateCustomerResourceAssembler.toResource(ap);

        LOG.debug("AgreementProduct received with id [{}]",response.getAgreementProductNumber());
        return new ResponseEntity<AgreementProductResource>(response,HttpStatus.OK);
        
    }
	
	@RequestMapping(value = "/searchByOVKCode", method = RequestMethod.GET)
    public ResponseEntity<?> findAgreementProductByOVKCode(@RequestParam(value="ovkCode") String ovkCode) throws AgreementProductNotFoundException{
        LOG.debug( "Request for findAgreementProductByOVKCode received with ovkCode [{}]",ovkCode);

     
        AgreementProduct ap = service.getAgreementProductByOVK(ovkCode);
        AgreementProductResourceAssembler corporateCustomerResourceAssembler = new AgreementProductResourceAssembler(AgreementProductController.class, 
        		AgreementProductResource.class);
        AgreementProductResource response = corporateCustomerResourceAssembler.toResource(ap);

        LOG.debug("AgreementProduct received with number [{}]",response.getAgreementProductNumber());
        return new ResponseEntity<AgreementProductResource>(response,HttpStatus.OK);
        
    }
	
	@ExceptionHandler(AgreementProductNotFoundException.class)
	public ResponseEntity<ClientErrorInformation> agreementProductNotFound(HttpServletRequest req, Exception e) 
	{
		ClientErrorInformation error = new ClientErrorInformation(e.toString(), req.getRequestURI());
		return new ResponseEntity<ClientErrorInformation>(error, HttpStatus.NOT_FOUND);
	}
	
}
