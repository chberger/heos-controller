package de.chberger.protocoll.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class TelnetClient {

	private static final Logger log = Logger.getLogger(TelnetClient.class.getName());

	private String ip;
	private int port;
	private boolean initialized;

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public TelnetClient(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
		initialized = false;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	/**
	 * Establish a telnet connection
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void initialize() {
		try {
			socket = new Socket(ip, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			initialized = true;
			log.info(String.format("Conncetion to %s:%s successfully established!", ip, port));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Send a command via the active telent connection
	 * 
	 * @param command
	 *            command to be send
	 * @return response data
	 * @throws IOException
	 */
	public String send(String command) {
		if (!initialized) {
			initialize();
		}
		try {
			log.fine(String.format("Sending command: %s", command));
			out.println(command);
			final String response = in.readLine();
			log.fine(String.format("Receive response: %s", response));
			return response;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Close the current telnet connection
	 * 
	 * @throws IOException
	 */
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
		initialized = false;
		log.info(String.format("Conncetion to %s:%s successfully closed!", ip, port));
	}

}
