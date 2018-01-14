package de.chberger.protocol.telnet;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

@Alternative
public class MultihreadedTelnetClient extends AbstractTelnetClient {
	
	@Inject
	private Logger logger;

	@Override
	public String send(String command) {
		// TODO Auto-generated method stub
		return null;
	}


}
