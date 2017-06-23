package com.fdc.customer.registry.web;

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

import com.fdc.customer.registry.aggregate.Customer;
import com.fdc.customer.registry.aggregate.CustomerRepository;

@RestController
@RequestMapping(value="/registry/customers")
public class CustomerController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomerRepository customerRepository;


	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Customer customer,Principal principal) {
        LOG.debug(Customer.class.getSimpleName() + " request received");
        
        Customer createdCustomer = customerRepository.save(customer);
        
        LOG.debug("Customer is saved with number [{}]",createdCustomer.getCustomerId());
        
        return new ResponseEntity<String>(createdCustomer.getCustomerId(),HttpStatus.CREATED);
    }
	
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllCustomers(){
        LOG.debug( "Request for findAllCustomers");
        
        List<CustomerResource> searchResponses = new ArrayList<CustomerResource>();
        CustomerResource searchResponse = null;
        CustomerResourceAssembler corporateCustomerResourceAssembler = new CustomerResourceAssembler(CustomerController.class, 
        		CustomerResource.class);
        for(Customer customer:customerRepository.findAll()){
        	searchResponse = corporateCustomerResourceAssembler.toResource(customer);
        	searchResponses.add(searchResponse);
        }
        //LOG.debug("Response from findAllCustomers - " + searchResponses.toString());
        return new ResponseEntity<>(searchResponses,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findCorporateCustomerByCustomerId(@PathVariable String id) throws CustomerNotFoundException{
        LOG.debug( "Request for findCorporateCustomerByCustomerId");
        Customer customer = customerRepository.findByCustomerId(id);
        if(null==customer){
        	throw new CustomerNotFoundException("No customer found for id " + id);
        }
        CustomerResourceAssembler corporateCustomerResourceAssembler = new CustomerResourceAssembler(CustomerController.class, 
        		CustomerResource.class);
        CustomerResource searchResponse = corporateCustomerResourceAssembler.toResource(customer);
        
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/searchByCustomerNationalId", method = RequestMethod.GET)
    public ResponseEntity<?> findCorporateCustomerByCustomerNationalId(@RequestParam(value="nationalId", defaultValue="123", required=true) String id)
    		throws CustomerNotFoundException
	{
        LOG.debug( "Request for findCorporateCustomerByCustomerNationalId with id " + id);
        
        Customer customer = customerRepository.findByNationalId(id);
        if(null==customer){
        	LOG.debug( "No customer found...throwing exception");
        	throw new CustomerNotFoundException("No employer agreement found for national id " + id);
        }
        CustomerResourceAssembler corporateCustomerResourceAssembler = new CustomerResourceAssembler(CustomerController.class, 
        		CustomerResource.class);
        CustomerResource searchResponse = corporateCustomerResourceAssembler.toResource(customer);
        LOG.debug( "Resource to be sent " + searchResponse.toString());
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/searchByCustomerName", method = RequestMethod.GET)
    public ResponseEntity<?> findCorporateCustomerByCustomerName(@RequestParam(value="name", defaultValue="Dummy", required=true) String name)
    		throws CustomerNotFoundException
	{
        LOG.debug( "Request for findCorporateCustomerByCustomerName");
        
        Customer customer = customerRepository.findByCustomerName(name);
        if(null==customer){
        	throw new CustomerNotFoundException("No employer agreement found for name " + name);
        }
        CustomerResourceAssembler corporateCustomerResourceAssembler = new CustomerResourceAssembler(CustomerController.class, 
        		CustomerResource.class);
        CustomerResource searchResponse = corporateCustomerResourceAssembler.toResource(customer);
        
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);

	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ClientErrorInformation> agreementProductNotFound(HttpServletRequest req, Exception e) {
		ClientErrorInformation error = new ClientErrorInformation(e.getMessage(), req.getRequestURI());
		return new ResponseEntity<ClientErrorInformation>(error, HttpStatus.NOT_FOUND);
	}

}
