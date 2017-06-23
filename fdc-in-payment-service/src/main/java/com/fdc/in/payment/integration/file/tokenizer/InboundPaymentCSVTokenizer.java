package com.fdc.in.payment.integration.file.tokenizer;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class InboundPaymentCSVTokenizer extends DelimitedLineTokenizer {

	public InboundPaymentCSVTokenizer(String delimiter) {
		super(delimiter);
		// TODO Auto-generated constructor stub
	}

}
