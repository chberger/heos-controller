package de.chberger.protocoll.telnet;

import java.io.IOException;
import java.net.InetAddress;

import javax.enterprise.inject.Specializes;

@Specializes
public class MockTelnetClient extends DefaultTelnetClient {

	@Override
	public void initialize(InetAddress ip, int port) {
		// Nothing to do

	}

	@Override
	public String send(String command) {
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
