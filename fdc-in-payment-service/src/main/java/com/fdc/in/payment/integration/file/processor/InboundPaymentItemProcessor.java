package com.fdc.in.payment.integration.file.processor;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import com.fdc.in.payment.aggregate.InboundPayment;

public class InboundPaymentItemProcessor implements ItemProcessor<InboundPayment, InboundPayment>{
	
	@Override
    public InboundPayment process(final InboundPayment payment) throws Exception {
		//set the record id
		UUID recordId = UUID.randomUUID();
		payment.setPaymentRecordId(recordId.toString());
        return payment;
    }

}
