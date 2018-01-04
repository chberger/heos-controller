package de.chberger.protocoll.ssdp.api;

import java.io.IOException;
import java.util.Set;

import de.chberger.protocoll.ssdp.UPNPDevice;
import de.chberger.protocoll.ssdp.types.ServiceType;

/**
 * Client for discovering UPNP devices with SSDP (Simple Service Discovery
 * Protocol).
 */
public interface SSDPClient {

	/**
	 * Discover any UPNP device using SSDP (Simple Service Discovery Protocol).
	 * 
	 * @return List of UPNP devices discovered
	 * @throws IOException
	 */
	public Set<UPNPDevice> discover() throws IOException;

	/**
	 * Discover specific UPNP devices using SSDP (Simple Service Discovery
	 * Protocol).
	 * 
	 * @param serviceType
	 *            type of UPNP devices to be discovered
	 * @return List of UPNP devices discovered
	 * @throws IOException
	 */
	public Set<UPNPDevice> discover(ServiceType serviceType) throws IOException;

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
	public Set<UPNPDevice> discover(int timeout, ServiceType serviceType) throws IOException;

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
	public UPNPDevice discoverOne(int timeout, ServiceType serviceType) throws IOException;

}
