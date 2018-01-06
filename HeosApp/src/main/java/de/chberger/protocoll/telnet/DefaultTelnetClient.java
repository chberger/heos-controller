package de.chberger.protocoll.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

import de.chberger.protocoll.telnet.api.TelnetClient;

@Default
public class DefaultTelnetClient implements TelnetClient {

	@Inject
	private Logger logger;

	private String ip;
	private int port;

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void initialize(InetAddress ip, int port) {
		this.ip = ip.getHostAddress();
		this.port = port;		
	}

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
			logger.trace(String.format("Telnet responseid <%s>: %s", id, response));
			return response;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void close() throws IOException {
		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
		if (socket != null) {
			socket.close();
		}
		logger.info(String.format("Telnet conncetion <%s:%s> has been closed!", ip, port));
	}
	
	private void connect() {
		try {
			socket = new Socket(ip, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			logger.info(String.format("Telnet conncetion <%s:%s> established!", ip, port));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean isConnected() {
		return this.socket!= null && socket.isConnected() && !socket.isClosed();
	}

}
