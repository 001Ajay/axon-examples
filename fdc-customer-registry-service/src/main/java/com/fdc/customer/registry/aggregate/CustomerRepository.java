package com.fdc.customer.registry.aggregate;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	@Query("{ 'customerId' : ?0 }")
	public Customer findByCustomerId(String customerId);
	
	@Query("{ 'nationalId' : ?0 }")
	public Customer findByNationalId(String id);
	
	@Query("{ 'name' : ?0 }")
	public Customer findByCustomerName(String name);
	
}
