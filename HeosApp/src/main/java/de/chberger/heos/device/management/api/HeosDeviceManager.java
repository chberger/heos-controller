package de.chberger.heos.device.management.api;

import java.io.IOException;
import java.util.Set;

import org.json.JSONObject;

import de.chberger.heos.device.HeosSpeaker;
import de.chberger.protocol.ssdp.UPNPDevice;

public interface HeosDeviceManager {

	/**
	 * Determine all Heos speakers to a given UPNP device
	 * 
	 * @param device
	 *            UPNP device that represents a Heos speaker
	 * @return Set of Heos speakers
	 * @throws IOException
	 */
	public Set<HeosSpeaker> getSpeakers(UPNPDevice device) throws IOException;

	/**
	 * Determine all Heos speakers to a given set of UPNP devices
	 * 
	 * @param devices
	 *            UPNP devices which represent Heos speakers
	 * @return Set of Heos speakers
	 * @throws IOException
	 */
	public Set<HeosSpeaker> getSpeakers(Set<UPNPDevice> devices) throws IOException;

	/**
	 * Send the passed command to the passed Heos speaker
	 * 
	 * @param comannd
	 *            Heos command to be issued. A command must start with the heos://
	 *            protocol identifier
	 * @param speaker
	 *            Heos speaker which should receive the command
	 * @return response as JSON object
	 * @throws IOException
	 */
	public JSONObject send(String comannd, HeosSpeaker speaker) throws IOException;

	/**
	 * Disconnect all telnet connections to Heos speaker associated with the device
	 * manager
	 * 
	 * @throws IOException
	 */
	public void closeAllConnections() throws IOException;

}
