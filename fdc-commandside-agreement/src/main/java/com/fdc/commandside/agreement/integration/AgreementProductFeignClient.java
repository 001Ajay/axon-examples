package com.fdc.commandside.agreement.integration;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "fdc-agreement-product-service", fallback = AgreementProductClientFallbackImpl.class)
//@FeignClient(value = "fdc-agreement-product-service")
public interface AgreementProductFeignClient extends AgreementProductClient{
}
