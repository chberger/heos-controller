package de.chberger.heos.device.management;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import de.chberger.protocol.telnet.api.TelnetClient;

@Alternative
public class MockTelnetClientManager extends TelnetClientManagerImpl {

	@Inject
	private TelnetClient client;

	@Override
	public TelnetClient getTelnetClient(InetAddress ip) throws UnknownHostException {
		return client;
	}

	@Override
	public List<TelnetClient> getAllTelnetClients() {
		List<TelnetClient> resultList = new ArrayList<>();
		resultList.add(client);
		return resultList;
	}

}
