package com.fdc.agreement.aggregate;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EmployerAgreementRepository extends MongoRepository<EmployerAgreement, String> {

	@Query("{ 'agreementId' : ?0 }")
	public EmployerAgreement findByAgreementId(String agreementId);
	
	@Query("{ 'cvr' : ?0 }")
	public List<EmployerAgreement> findByCVR(String cvr);
	
	@Query("{ 'customerNumber' : ?0 }")
	public List<EmployerAgreement> findByCustomerNumber(String number);
	
}
