package com.fdc.commandside.agreement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fdc.commandside.agreement.integration.AgreementProductClient;
import com.fdc.commandside.agreement.web.AgreementController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = AgreementProductClient.class)
public class Application {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public AgreementController agreementController() {
		return new AgreementController();
	}
}
