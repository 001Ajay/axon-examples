package com.fdc.commandside.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.fdc.commandside.customer.configuration.AxonConfiguration;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	
	private static final Logger LOG = LoggerFactory.getLogger(AxonConfiguration.class);
	
	/**
	 * This event is executed as late as conceivably possible to indicate that
	 * the application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		LOG.debug("Application started....");

		return;
	}

} // class
