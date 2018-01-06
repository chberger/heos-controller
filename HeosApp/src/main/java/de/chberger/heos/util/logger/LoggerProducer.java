package de.chberger.heos.util.logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logging producer for injectable java util logger 
 * @author chberger
 *
 */
public class LoggerProducer {

	@Produces
	public Logger produceLogger(InjectionPoint injectionPoint) {
		Logger logger = LogManager.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
		return logger;
	}

}
