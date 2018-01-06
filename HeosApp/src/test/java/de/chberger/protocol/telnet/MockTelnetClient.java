package de.chberger.protocol.telnet;

import java.io.IOException;
import java.net.InetAddress;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

import de.chberger.protocol.telnet.api.TelnetClient;

@Alternative
public class MockTelnetClient implements TelnetClient {
	
	@Inject
	private Logger logger;

	@Override
	public void initialize(InetAddress ip, int port) {
		// Nothing to do

	}

	@Override
	public String send(String command) {
		logger.debug(String.format("MockTelnetClient is being called with: %s",command));
		switch (command) {
		case "heos://player/get_players":
			return "{\"heos\": {\"command\": \"player/get_players\",\"result\": \"success\",\"message\": \"\"},"+
				"\"payload\": [{\"name\": \"player name 1\",\"pid\": \"1\",\"gid\": \"group id\",\"ip\": \"127.0.0.1\",\"model\": \"'player model 1'\",\"version\": \"'player verison 1'\",\"lineout\": \"0\",\"network\": \"wifi\"}]}";
		case "heos://player/set_play_state?pid=1&state=play":
			return "{\"heos\": {\"command\": \" player/set_play_state \", \"result\": \"success\", \"message\": \"pid=1&state=play\"}}";
		case "heos://player/set_play_state?pid=1&state=stop":
			return "{\"heos\": {\"command\": \" player/set_play_state \", \"result\": \"success\", \"message\": \"pid=1&state=stop\"}}";
		default:
			return null;
		}
	}

	@Override
	public void close() throws IOException {
		// Nothing to do

	}

}
