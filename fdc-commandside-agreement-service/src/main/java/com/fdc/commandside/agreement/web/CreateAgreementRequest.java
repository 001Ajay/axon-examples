package com.fdc.commandside.agreement.web;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateAgreementRequest {

	@NonNull private String cvr;
	@NonNull private String name;
	@NonNull private String pnumber;
	@NonNull private List<String> ovkCodes;

}
