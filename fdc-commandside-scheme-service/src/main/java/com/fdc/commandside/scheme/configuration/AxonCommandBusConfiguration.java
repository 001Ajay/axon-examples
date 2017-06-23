package com.fdc.commandside.scheme.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fdc.common.command.AuditableAbstractCommand;

@Configuration
public class AxonCommandBusConfiguration {
	
	/*@Bean
	public Serializer serializer() {
		return new JacksonSerializer();
	}*/

	@Bean
	public RestOperations restTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	public CommandRouter commandRouter(DiscoveryClient discoveryClient) {
	    return new SpringCloudCommandRouter(discoveryClient, new AnnotationRoutingStrategy());
	}
	
	@Bean
    public CommandBusConnector commandBusConnector(@Qualifier("localSegment") CommandBus localSegment,
                                                             RestOperations restTemplate,
                                                             Serializer serializer) {
        return new SpringHttpCommandBusConnector(localSegment, (RestTemplate) restTemplate, serializer);
    }
	
    
    @Primary // to make sure this CommandBus implementation is used for autowiring
    @Bean
    public DistributedCommandBus springCloudDistributedCommandBus(CommandRouter commandRouter, 
                                                                  CommandBusConnector commandBusConnector) {
        return new DistributedCommandBus(commandRouter, commandBusConnector);
    }
    
    @Bean("commandGateway")
	public CommandGatewayFactoryBean<AuditableAbstractCommand> commandGateway(DistributedCommandBus springCloudDistributedCommandBus){
		
		CommandGatewayFactoryBean<AuditableAbstractCommand> commandGatewayFactoryBean=new CommandGatewayFactoryBean<AuditableAbstractCommand>();
		commandGatewayFactoryBean.setCommandBus(springCloudDistributedCommandBus);
		return commandGatewayFactoryBean;
	}
}
