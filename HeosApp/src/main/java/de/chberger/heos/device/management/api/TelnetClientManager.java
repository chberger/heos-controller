package de.chberger.heos.device.management.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import de.chberger.protocol.telnet.api.TelnetClient;

public interface TelnetClientManager {
	
	public TelnetClient getTelnetClient(InetAddress ip) throws UnknownHostException;
	
	public List<TelnetClient> getAllTelnetClients();

}
