package de.chberger.protocol.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.exceptions.IllegalStateException;

import de.chberger.protocol.telnet.api.TelnetClient;

@Alternative
public abstract class AbstractTelnetClient extends Thread implements TelnetClient {
	
	@Inject
	private Logger logger;
	
	protected String ip;
	protected int port;

	protected Socket socket;
	protected PrintWriter out;
	protected BufferedReader in;

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}
	
	@Override
	public void initialize(InetAddress ip, int port) {
		if (!isInitalized()) {
			this.ip = ip.getHostAddress();
			this.port = port;
			logger.info(String.format("Telnet client <%s:%s> successfully initalized!", ip, port));
		} else {
			logger.warn("Telnet client already initalized!");
		}
	}
	
	@Override
	public abstract String send(String command);

	@Override
	public void close() throws IOException {
		ip = null;
		port = 0;

		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
		if (socket != null) {
			socket.close();
		}
		logger.info(String.format("Telnet conncetion to <%s:%s> has been closed!", ip, port));
	}

	protected void connect() {
		if (isInitalized()) {
			try {
				socket = new Socket(ip, port);
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				logger.info(String.format("Telnet conncetion <%s:%s> sucessfully established!", ip, port));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new IllegalStateException("Unable to connect. Please initalize a telnet client first!");
		}
	}

	protected boolean isConnected() {
		return this.socket != null && socket.isConnected() && !socket.isClosed();
	}

	private boolean isInitalized() {
		return StringUtils.isNotEmpty(this.ip) && this.port != 0;
	}


}
