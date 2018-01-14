package de.chberger.protocol.telnet;

import java.io.IOException;
import java.util.UUID;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

@Default
public class SimpleTelnetClient extends AbstractTelnetClient {

	@Inject
	private Logger logger;
	
	@Override
	public String send(String command) {
		if (!isConnected()) {
			connect();
		}
		try {
			UUID id = UUID.randomUUID();
			logger.trace(String.format("Telnet request id<%s>: %s", id, command));
			out.println(command);
			final String response = in.readLine();
			logger.trace(String.format("Telnet response id <%s>: %s", id, response));
			return response;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
}
