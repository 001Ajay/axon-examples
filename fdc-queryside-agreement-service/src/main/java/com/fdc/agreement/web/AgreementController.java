package com.fdc.agreement.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdc.agreement.aggregate.EmployerAgreement;
import com.fdc.agreement.aggregate.EmployerAgreementRepository;


@RestController
@RequestMapping(value="/employer/agreements")
public class AgreementController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmployerAgreementRepository employerAgreementRepository;

	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllEmployerAgreements(){
        LOG.debug( "Request for findAllEmployerAgreements");
        
        List<EmployerAgreementResource> searchResponses = new ArrayList<EmployerAgreementResource>();
        EmployerAgreementResourceAssembler employerAgreementResourceAssembler = new EmployerAgreementResourceAssembler(AgreementController.class, 
        		EmployerAgreementResource.class);
        EmployerAgreementResource searchResponse = null;
        for(EmployerAgreement agreement:employerAgreementRepository.findAll()){
        	searchResponse = employerAgreementResourceAssembler.toResource(agreement);
        	searchResponses.add(searchResponse);
        }
        
        return new ResponseEntity<>(searchResponses,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findEmployerAgreementById(@PathVariable String id) throws AgreementNotFoundException{
        LOG.debug( "Request for findEmployerAgreementByCVR");
        EmployerAgreement agreement = employerAgreementRepository.findOne(id);
        if(null==agreement){
        	throw new AgreementNotFoundException("No employer agreement found for id " + id);
        }
        EmployerAgreementResourceAssembler employerAgreementResourceAssembler = new EmployerAgreementResourceAssembler(AgreementController.class, 
        		EmployerAgreementResource.class);
        EmployerAgreementResource searchResponse = employerAgreementResourceAssembler.toResource(agreement);
        
        return new ResponseEntity<>(searchResponse,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/searchByCustomerNumber", method = RequestMethod.GET)
    public ResponseEntity<?> findEmployerAgreementByCustomerNumber(@RequestParam(value="customerNumber") String customerNumber)
    		throws AgreementNotFoundException
	{
        LOG.debug( "Request for findEmployerAgreementByCustomerNumber");
        
        //List<EmployerAgreementResource> agreements = employerAgreementRepository.findByCustomerNumber(customerNumber);
        List<EmployerAgreementResource> searchResponses = new ArrayList<EmployerAgreementResource>();
        EmployerAgreementResourceAssembler employerAgreementResourceAssembler = new EmployerAgreementResourceAssembler(AgreementController.class, 
        		EmployerAgreementResource.class);
        EmployerAgreementResource searchResponse = null;
        for(EmployerAgreement agreement:employerAgreementRepository.findByCustomerNumber(customerNumber)){
        	searchResponse = employerAgreementResourceAssembler.toResource(agreement);
        	searchResponses.add(searchResponse);
        }
        
        return new ResponseEntity<>(searchResponses,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/searchByCVR", method = RequestMethod.GET)
    public ResponseEntity<?> findEmployerAgreementByCVR(@RequestParam(value="cvr") String cvr)
    		throws AgreementNotFoundException
	{
        LOG.debug( "Request for findEmployerAgreementByCVR");
        
       // EmployerAgreement agreement = employerAgreementRepository.findByCVR(cvr);
        List<EmployerAgreementResource> searchResponses = new ArrayList<EmployerAgreementResource>();
        EmployerAgreementResourceAssembler employerAgreementResourceAssembler = new EmployerAgreementResourceAssembler(AgreementController.class, 
        		EmployerAgreementResource.class);
        EmployerAgreementResource searchResponse = null;
        for(EmployerAgreement agreement:employerAgreementRepository.findByCVR(cvr)){
        	searchResponse = employerAgreementResourceAssembler.toResource(agreement);
        	searchResponses.add(searchResponse);
        }
        return new ResponseEntity<>(searchResponses,HttpStatus.OK);
	}

	@ExceptionHandler(AgreementNotFoundException.class)
	public ResponseEntity<ClientErrorInformation> agreementProductNotFound(HttpServletRequest req, Exception e) {
		ClientErrorInformation error = new ClientErrorInformation(e.getMessage(), req.getRequestURI());
		return new ResponseEntity<ClientErrorInformation>(error, HttpStatus.NOT_FOUND);
	}

}
