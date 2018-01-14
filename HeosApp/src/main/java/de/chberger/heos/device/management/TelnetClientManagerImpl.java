package de.chberger.heos.device.management;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Provider;

import de.chberger.heos.device.management.api.TelnetClientManager;
import de.chberger.protocol.telnet.api.TelnetClient;

@Default
@ApplicationScoped
public class TelnetClientManagerImpl implements TelnetClientManager {
	
	private static final int HEOS_DEFAULT_PORT = 1255;

	@Inject
	protected Provider<TelnetClient> clientProvider;

	private Map<InetAddress, List<TelnetClient>> clients = new HashMap<>();

	@Override
	public TelnetClient getTelnetClient(InetAddress ip) throws UnknownHostException {
		TelnetClient client = null;
		if (!hasIp(ip)) {
			client = clientProvider.get();
			client.initialize(ip, HEOS_DEFAULT_PORT);
		} else {
			//TODO: handle list properly
			clients.get(ip).get(0);
		}
		return client;
	}

	@Override
	public List<TelnetClient> getAllTelnetClients() {
		List<TelnetClient> resultList = new ArrayList<>();
		for (Map.Entry<InetAddress, List<TelnetClient>> entry : clients.entrySet()) {
			for (TelnetClient telnetClient : entry.getValue()) {
				resultList.add(telnetClient);
			}
		}
		return resultList;
	}

	private boolean hasIp(InetAddress ip) {
		return clients.containsKey(ip);
	}
}
