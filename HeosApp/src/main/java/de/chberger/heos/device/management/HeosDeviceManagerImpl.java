package de.chberger.heos.device.management;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.jboss.weld.exceptions.IllegalArgumentException;
import org.json.JSONArray;
import org.json.JSONObject;

import de.chberger.heos.device.HeosSpeaker;
import de.chberger.heos.device.management.api.HeosDeviceManager;
import de.chberger.heos.device.management.api.TelnetClientManager;
import de.chberger.protocol.ssdp.UPNPDevice;
import de.chberger.protocol.ssdp.types.ServiceType;
import de.chberger.protocol.telnet.api.TelnetClient;

@Default
public class HeosDeviceManagerImpl implements HeosDeviceManager {

	private static final String HEOS_PROTOCOL = "heos";
	private static final String HEOS_GET_ALL_PLAYERS = HEOS_PROTOCOL + "://player/get_players";
	private static final String HEOS_RESULT = "result";
	
	@Inject
	protected TelnetClientManager tcManager;
	
	@Override
	public Set<HeosSpeaker> getSpeakers(UPNPDevice device) throws IOException {

		if (device.getServiceType().equals(ServiceType.HEOS.getUrn())) {
			return parseJSON(this.getPlayers(device));
		} else {
			throw new IllegalArgumentException(String.format(
					"Unable to mannage device with service type <%s>! Only Heos devices <%s> are permitted!",
					device.getServiceType(), ServiceType.HEOS.getUrn()));
		}
	}

	@Override
	public Set<HeosSpeaker> getSpeakers(Set<UPNPDevice> devices) throws IOException {
		Set<HeosSpeaker> result = new HashSet<HeosSpeaker>();
		for (UPNPDevice device : devices) {
			result.addAll(getSpeakers(device));
		}
		return result;
	}

	@Override
	public JSONObject send(String heosCommand, HeosSpeaker speaker) throws IOException {
		return send(heosCommand, speaker.getIp());
	}
	
	@Override
	public void closeAllConnections() throws IOException {
		for (TelnetClient client : tcManager.getAllTelnetClients()) {
			client.close();		
		}
	}

	private JSONObject getPlayers(UPNPDevice device) throws IOException {
		return send(HEOS_GET_ALL_PLAYERS, device.getIPAddress());
	}

	private JSONObject send(String heosCommand, String ip) throws IOException {
		assert (heosCommand.startsWith(HEOS_PROTOCOL)) : "Unknwon protocol! Only " + HEOS_PROTOCOL
				+ ":// is supported.";
		final TelnetClient client = tcManager.getTelnetClient(InetAddress.getByName(ip));
		final JSONObject response = new JSONObject(client.send(heosCommand));
		verifyResponse(response);
		return response;
	}

	private void verifyResponse(JSONObject response) {
		if (!(response.getJSONObject(HEOS_PROTOCOL).getString(HEOS_RESULT).equals("success"))) {
			throw new IllegalStateException("A communication error has been occurred!");
		}
	}

	private Set<HeosSpeaker> parseJSON(JSONObject response) {
		Set<HeosSpeaker> speakers = new HashSet<HeosSpeaker>();
		final JSONArray players = response.getJSONArray("payload");
		for (int i = 0; i < players.length(); i++) {
			JSONObject player = players.getJSONObject(i);
			HeosSpeaker speaker = new HeosSpeaker(player.getString("name"), player.getLong("pid"),
					player.optLong("gid", -1), player.getString("ip"), player.getString("model"),
					player.getString("version"), player.getLong("lineout"), player.getString("network"));
			speakers.add(speaker);
		}
		return speakers;
	}

}
