package com.fdc.agreementProduct.aggregate;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdc.common.agreementProduct.model.PolicyProductModel;

@Component
public class AgreementProductService {

	@Autowired
	AgreementProductRepository repository;
	
	public String saveAgreementProduct(String ovkAgreementNumber, String ovkCode,
			String agreementProductName,String agreementProductType,List<PolicyProductModel> policyProducts){
		
		String agreementProductNumber = getAgreementProductNumber();
        AgreementProduct agreementProduct = new AgreementProduct(agreementProductNumber, ovkAgreementNumber,
        		ovkCode, agreementProductName, agreementProductType);
        agreementProduct.setPolicyProducts(policyProducts);
        
        repository.save(agreementProduct);
        
        return agreementProductNumber;
	}
	
	public List<AgreementProduct> getAgreementProducts() throws AgreementProductNotFoundException{
		List<AgreementProduct> agreementProducts = repository.findAll();
		
		if (agreementProducts==null)throw new AgreementProductNotFoundException("No agreement product is there, read store is empty");
		
		return agreementProducts;
	}
	
	public AgreementProduct getAgreementProductById(String id) throws AgreementProductNotFoundException{
		AgreementProduct agreementProduct = repository.findOne(id);
		
		if (agreementProduct==null)throw new AgreementProductNotFoundException("No agreement product found for agreement product id " + id);
		
		return agreementProduct;
	}
	
	public AgreementProduct getAgreementProductByOVK(String ovkCode) throws AgreementProductNotFoundException{
		AgreementProduct agreementProduct = repository.findByovkAgreementCode(ovkCode);
		
		if (agreementProduct==null)throw new AgreementProductNotFoundException("No agreement product found for ovkCode " + ovkCode);
		
		return agreementProduct;
	}
	
	private String getAgreementProductNumber() {
		return UUID.randomUUID().toString();
	}
}
