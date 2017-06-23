package com.fdc.commandside.customer.configuration;

import javax.sql.DataSource;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.common.AmqpServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class PivotalCloudConfig extends AbstractCloudConfig {
	
	@Value("${datasource:fdc_customer_eventstore}")
	String datasource;

	@Value("${event_bus:event-bus}")
	String event_bus;
	
	@Bean
	public DataSource dataSource() {
		return connectionFactory().dataSource(datasource);
	}

	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		AmqpServiceInfo serviceInfo = (AmqpServiceInfo) cloud.getServiceInfo(event_bus);
		String serviceID = serviceInfo.getId();
		return cloud.getServiceConnector(serviceID, ConnectionFactory.class, null);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

}
