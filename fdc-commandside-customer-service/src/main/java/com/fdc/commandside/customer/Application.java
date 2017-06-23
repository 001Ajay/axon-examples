package com.fdc.commandside.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import com.fdc.commandside.customer.integration.CustomerRegistryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = CustomerRegistryClient.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}
	
	
}
