package de.chberger.protocol.ssdp;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Alternative;

import de.chberger.protocol.ssdp.UPNPDevice;
import de.chberger.protocol.ssdp.api.SSDPClient;
import de.chberger.protocol.ssdp.types.ServiceType;

@Alternative
public class MockSSDPClient implements SSDPClient {

	private UPNPDevice device = new UPNPDevice("127.0.0.1", "heos.device.xml", "localhost", ServiceType.HEOS.getUrn(),
			"usn:xxx");

	@Override
	public Set<UPNPDevice> discover() throws IOException {
		Set<UPNPDevice> devices = new HashSet<UPNPDevice>();
		devices.add(device);
		return devices;
	}

	@Override
	public Set<UPNPDevice> discover(ServiceType serviceType) throws IOException {
		return this.discover(1500, serviceType);
	}

	@Override
	public Set<UPNPDevice> discover(int timeout, ServiceType serviceType) throws IOException {
		Set<UPNPDevice> devices = new HashSet<UPNPDevice>();
		if (serviceType.equals(ServiceType.HEOS)) {
			devices.add(device);
		}
		return devices;
	}

	@Override
	public UPNPDevice discoverSingleDevice(int timeout, ServiceType serviceType) throws IOException {
		return serviceType.equals(ServiceType.HEOS)? device : null;
	}

}
