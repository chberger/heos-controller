package de.chberger.protocol.telnet.api;

import java.io.IOException;
import java.net.InetAddress;

public interface TelnetClient {
	
	/**
	 * Initialize a connection
	 */
	public void initialize(InetAddress ip, int port);
	
	/**
	 * Send a command through the active connection
	 * 
	 * @param command
	 *            command to be send
	 * @return response data
	 */
	public String send(String command);
	
	/**
	 * Close an active connection
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;
	

}
