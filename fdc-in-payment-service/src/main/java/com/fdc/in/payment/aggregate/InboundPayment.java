package com.fdc.in.payment.aggregate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Document(collection = "inboundPayment")
public class InboundPayment {

	private static final Logger LOG = LoggerFactory.getLogger(InboundPayment.class);
	
	@NonNull @Id private String paymentRecordId;
	@NonNull private String cvr;	
	@NonNull private String cpr;
	@NonNull private String ovkAgreementNumber;
	@NonNull private String amount;	

}