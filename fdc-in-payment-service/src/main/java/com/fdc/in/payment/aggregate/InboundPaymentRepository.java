package com.fdc.in.payment.aggregate;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InboundPaymentRepository extends MongoRepository<InboundPayment, String> {

	
}
