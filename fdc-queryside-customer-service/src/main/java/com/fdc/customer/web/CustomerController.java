package com.fdc.customer.web;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdc.customer.aggregate.CorporateCustomer;
import com.fdc.customer.aggregate.CorporateCustomerRepository;

@RestController
@RequestMapping(value="/corporate/customers")
public class CustomerController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CorporateCustomerRepository corportateCustomerRepository;


	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody CorporateCustomer customer,Principal principal) {
        LOG.debug(CorporateCustomer.class.getSimpleName() + " request received");
        
        CorporateCustomer createdCustomer = corportateCustomerRepository.save(customer);
        
        LOG.debug("Customer is saved with number [{}]",createdCustomer.getCustomerId());
        
        return new ResponseEntity<String>(createdCustomer.getCustomerId(),HttpStatus.CREATED);
    }
	
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllCustomers(){
        LOG.debug( "Request for findAllCustomers");
        
        List<CorporateCustomerResource> searchResponses = new ArrayList<CorporateCustomerResource>();
        CorporateCustomerResource searchResponse = null;
        CorporateCustomerResourceAssembler corporateCustomerResourceAssembler = new CorporateCustomerResourceAssembler(CustomerController.class, 
        		CorporateCustomerResource.class);
        for(CorporateCustomer customer:corportateCustomerRepository.findAll()){
        	searchResponse = corporateCustomerResourceAssembler.toResource(customer);
        	searchResponses.add(searchResponse);
        }
        //LOG.debug("Response from findAllCustomers - " + searchResponses.toString());
        return new ResponseEntity<>(searchResponses,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findCorporateCustomerByCustomerId(@PathVariable String id) throws CustomerNotFoundException{
        LOG.debug( "Request for findCorporateCustomerByCustomerId");
        CorporateCustomer customer = corportateCustomerRepository.findByCustomerId(id);
        if(null==customer){
        	throw new CustomerNotFoundException("No customer found for id " + id);
        }
        CorporateCustomerResourceAssembler corporateCustomerResourceAssembler = new CorporateCustomerResourceAssembler(CustomerController.class, 
        		CorporateCustomerResource.class);
        CorporateCustomerResource searchResponse = corporateCustomerResourceAssembler.toResource(customer);
        
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/searchByCustomerCVR", method = RequestMethod.GET)
    public ResponseEntity<?> findCorporateCustomerByCustomerCVR(@RequestParam(value="cvr", defaultValue="123", required=true) String cvr)
    		throws CustomerNotFoundException
	{
        LOG.debug( "Request for findCorporateCustomerByCustomerCVR");
        
        CorporateCustomer customer = corportateCustomerRepository.findByCVR(cvr);
        if(null==customer){
        	throw new CustomerNotFoundException("No employer agreement found for cvr " + cvr);
        }
        CorporateCustomerResourceAssembler corporateCustomerResourceAssembler = new CorporateCustomerResourceAssembler(CustomerController.class, 
        		CorporateCustomerResource.class);
        CorporateCustomerResource searchResponse = corporateCustomerResourceAssembler.toResource(customer);
        
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/searchByCustomerName", method = RequestMethod.GET)
    public ResponseEntity<?> findCorporateCustomerByCustomerName(@RequestParam(value="name", defaultValue="Dummy", required=true) String name)
    		throws CustomerNotFoundException
	{
        LOG.debug( "Request for findCorporateCustomerByCustomerName");
        
        CorporateCustomer customer = corportateCustomerRepository.findByCustomerName(name);
        if(null==customer){
        	throw new CustomerNotFoundException("No employer agreement found for name " + name);
        }
        CorporateCustomerResourceAssembler corporateCustomerResourceAssembler = new CorporateCustomerResourceAssembler(CustomerController.class, 
        		CorporateCustomerResource.class);
        CorporateCustomerResource searchResponse = corporateCustomerResourceAssembler.toResource(customer);
        
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);

	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ClientErrorInformation> agreementProductNotFound(HttpServletRequest req, Exception e) {
		ClientErrorInformation error = new ClientErrorInformation(e.getMessage(), req.getRequestURI());
		return new ResponseEntity<ClientErrorInformation>(error, HttpStatus.NOT_FOUND);
	}

}
