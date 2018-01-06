package de.chberger.heos.device.management;

import java.io.IOException;
import java.util.Set;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.json.JSONObject;

import de.chberger.heos.device.HeosSpeaker;
import de.chberger.heos.device.management.api.HeosDeviceManager;
import de.chberger.protocol.ssdp.UPNPDevice;
import de.chberger.protocol.telnet.api.TelnetClient;

@Alternative
public class MockHeosDeviceManager extends HeosDeviceManagerImpl implements HeosDeviceManager {
	
	@Inject	
	private void setTelnetClient(TelnetClient client) {
		this.client = client;
	}

	@Override
	public Set<HeosSpeaker> getSpeakers(UPNPDevice device) throws IOException {
		return super.getSpeakers(device);
	}

	@Override
	public Set<HeosSpeaker> getSpeakers(Set<UPNPDevice> devices) throws IOException {
		return super.getSpeakers(devices);
	}

	@Override
	public JSONObject send(String heosCommand, HeosSpeaker speaker) throws IOException {
		return super.send(heosCommand, speaker);
	}

}
