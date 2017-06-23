package com.fdc.customer.aggregate;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CorporateCustomerRepository extends MongoRepository<CorporateCustomer, String> {

	@Query("{ 'customerId' : ?0 }")
	public CorporateCustomer findByCustomerId(String customerId);
	
	@Query("{ 'cvr' : ?0 }")
	public CorporateCustomer findByCVR(String cvr);
	
	@Query("{ 'companyName' : ?0 }")
	public CorporateCustomer findByCustomerName(String name);
	
}
