package com.fdc.common.agreement.model;

public class SchemeModel {

	private String name;
	private String code;
	
	
	
	public SchemeModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SchemeModel(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
