package de.chberger.heos.control;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import de.chberger.heos.devices.Speaker;
import de.chberger.protocoll.ssdp.SSDPClient;
import de.chberger.protocoll.ssdp.types.ServiceType;
import de.chberger.protocoll.ssdp.types.UPNPDevice;

public class App {

	private static final Logger log = Logger.getLogger(App.class.getName());

	public static void main(String[] args) throws IOException {
		log.info("Discovering heos devices ...");
		Set<UPNPDevice> devices = SSDPClient.discover(ServiceType.HEOS);
		log.info(String.format("%s devices found", devices.size()));
		
		for (UPNPDevice device : devices) {
			Set<Speaker> speakers = DeviceControl.registerDevice(device);
			log.info("Show found speakers ...");
			for (Speaker speaker : speakers) {
				log.info(speaker.toString());
			}
			DeviceControl.unregsiterDevice();
		}
		
	}
}
