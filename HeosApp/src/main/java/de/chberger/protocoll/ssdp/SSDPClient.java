package de.chberger.protocoll.ssdp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.chberger.protocoll.ssdp.types.ServiceType;
import de.chberger.protocoll.ssdp.types.UPNPDevice;

/**
 * Client for discovering UPNP devices with SSDP (Simple Service Discovery
 * Protocol).
 */
public class SSDPClient {

	private static final int DEFAULT_DISCOVER_TIMEOUT = 1500;
	private static final int MULTICAST_PORT = 1900;
	private static final int MX_DEFAULT = 5;
	private static final String MULTICAST_ADRESS = "239.255.255.250";
	private static final String LINE_END = "\r\n";

	/**
	 * Discover any UPNP device using SSDP (Simple Service Discovery Protocol).
	 * 
	 * @return List of UPNP devices discovered
	 * @throws IOException
	 */
	public static Set<UPNPDevice> discover() throws IOException {
		return discover(ServiceType.DEFAULT);
	}

	/**
	 * Discover specific UPNP devices using SSDP (Simple Service Discovery
	 * Protocol).
	 * 
	 * @param serviceType
	 *            type of UPNP devices to be discovered
	 * @return List of UPNP devices discovered
	 * @throws IOException
	 */
	public static Set<UPNPDevice> discover(ServiceType serviceType) throws IOException {
		return discover(DEFAULT_DISCOVER_TIMEOUT, serviceType);
	}

	/**
	 * Discover specific UPNP device using SSDP (Simple Service Discovery Protocol).
	 * 
	 * @param timeout
	 *            in milliseconds
	 * @param serviceType
	 *            type of UPNP devices to be discovered
	 * @return List of UPNP devices discovered
	 * @throws IOException
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Simple_Service_Discovery_Protocol">SSDP
	 *      Wikipedia Page</a>
	 */
	public static Set<UPNPDevice> discover(int timeout, ServiceType serviceType) throws IOException {
		Set<UPNPDevice> devices = new HashSet<UPNPDevice>();

		byte[] sendData = createDiscoveryRequest(serviceType).getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
				InetAddress.getByName(MULTICAST_ADRESS), MULTICAST_PORT);
		DatagramSocket clientSocket = new DatagramSocket();
		clientSocket.setSoTimeout(timeout);
		clientSocket.send(sendPacket);

		byte[] receiveData = new byte[1024];

		/* collect all devices till we reach the timeout */
		while (true) {
			try {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				devices.add(UPNPDevice.parse(receivePacket));
			} catch (SocketTimeoutException e) {
				break;
			}
		}

		if (clientSocket != null) {
			clientSocket.close();
		}

		return Collections.unmodifiableSet(devices);
	}

	/**
	 * Discover a single UPNP device using SSDP (Simple Service Discovery Protocol).
	 * Might return null if no device has been found.
	 * 
	 * @param timeout
	 *            in milliseconds
	 * @param serviceType
	 *            type of UPNP device to be discovered
	 * @return a single UPNP device
	 * @throws IOException
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Simple_Service_Discovery_Protocol">SSDP
	 *      Wikipedia Page</a>
	 */
	public static UPNPDevice discoverOne(int timeout, ServiceType serviceType) throws IOException {
		Set<UPNPDevice> devices = discover(timeout, serviceType);
		if (!devices.isEmpty() && devices.iterator().hasNext()) {
			return devices.iterator().next();
		} else {
			return null;
		}
	}

	private static String createDiscoveryRequest(ServiceType serviceType) {

		StringBuilder request = new StringBuilder();
		request.append("M-SEARCH * HTTP/1.1").append(LINE_END);
		request.append("Host: " + MULTICAST_ADRESS + ":" + MULTICAST_PORT).append(LINE_END);
		request.append("Man: \"ssdp:discover\"").append(LINE_END);
		request.append("ST: ").append(serviceType.getUrn()).append(LINE_END);
		request.append("MX: ").append(MX_DEFAULT).append(LINE_END);
		if (serviceType.getUrn().contains("udap")) {
			request.append("USER-AGENT: UDAP/2.0").append(LINE_END);
		}
		request.append(LINE_END);
		return request.toString();
	}

}
