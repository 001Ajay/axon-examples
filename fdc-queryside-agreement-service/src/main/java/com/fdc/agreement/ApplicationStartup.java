package com.fdc.agreement;

import org.axonframework.serialization.xml.XStreamSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationStartup.class);
	
	/**
	 * This event is executed as late as conceivably possible to indicate that
	 * the application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		/*ApplicationContext context = event.getApplicationContext();
		XStreamSerializer serializer = (XStreamSerializer)context.getBean("serializer");
		XStream stream = serializer.getXStream();
		stream.autodetectAnnotations(true);
		
		LOG.debug("Application started....");
*/
		return;
	}

} // class
