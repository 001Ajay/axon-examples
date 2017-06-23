package com.fdc.agreementProduct.aggregate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AgreementProductRepository extends MongoRepository<AgreementProduct, String> {

	@Query("{ 'ovkAgreementCode' : ?0 }")
	public AgreementProduct findByovkAgreementCode(String ovkAgreementCode);
	
	
}
