package com.fdc.common.scheme.model;

import java.util.List;

import com.fdc.common.util.AgreementType;

public class AgreementModel {

	private String code;
	private AgreementType type;
	private List<String> productList;
	
	
	
	public AgreementModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgreementModel(String code, AgreementType type, List<String> productList) {
		super();
		this.code = code;
		this.type = type;
		this.productList = productList;
	}

	public String getCode() {
		return code;
	}

	public AgreementType getType() {
		return type;
	}

	public List<String> getProductList() {
		return productList;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setType(AgreementType type) {
		this.type = type;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}
	
	
}
