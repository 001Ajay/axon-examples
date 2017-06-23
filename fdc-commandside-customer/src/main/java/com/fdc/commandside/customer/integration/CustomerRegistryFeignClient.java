package com.fdc.commandside.customer.integration;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "fdc-customer-registry-service", fallback = CustomerRegistryClientFallbackImpl.class)
//@FeignClient(value = "fdc-agreement-product-service")
public interface CustomerRegistryFeignClient extends CustomerRegistryClient{
}
