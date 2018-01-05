package de.chberger.heos.device.management;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.weld.exceptions.IllegalArgumentException;
import org.json.JSONArray;
import org.json.JSONObject;

import de.chberger.heos.device.HeosSpeaker;
import de.chberger.protocoll.ssdp.UPNPDevice;
import de.chberger.protocoll.ssdp.types.ServiceType;
import de.chberger.protocoll.telnet.api.TelnetClient;

public class HeosDeviceManager {

	private static final String HEOS_PROTOCOLL = "heos";
	private static final String HEOS_GET_ALL_PLAYERS = HEOS_PROTOCOLL + "://player/get_players";
	private static final String HEOS_JSON_RESULT = "result";
	private static final int HEOS_DEFAULT_PORT = 1255;

	@Inject
	private TelnetClient client;

	public Set<HeosSpeaker> getSpeakers(UPNPDevice device) throws IOException {

		if (device.getServiceType().equals(ServiceType.HEOS.getUrn())) {
			return parseJSON(this.getPlayers(device));
		} else {
			throw new IllegalArgumentException(String.format(
					"Unable to mannage device with service type <%s>! Only Heos devices <%s> are permitted!",
					device.getServiceType(), ServiceType.HEOS.getUrn()));
		}
	}

	public Set<HeosSpeaker> getSpeakers(Set<UPNPDevice> devices) throws IOException {
		Set<HeosSpeaker> result = new HashSet<HeosSpeaker>();
		for (UPNPDevice device : devices) {
			result.addAll(getSpeakers(device));
		}
		return result;
	}
	
	public JSONObject send(String heosCommand, HeosSpeaker speaker) throws IOException {
		return send(heosCommand, speaker.getIp());
	}

	private JSONObject getPlayers(UPNPDevice device) throws IOException {
		return send(HEOS_GET_ALL_PLAYERS, device.getIPAddress());
	}
	
	private JSONObject send(String heosCommand, String ip) throws IOException {
		assert(heosCommand.startsWith(HEOS_PROTOCOLL));
		client.initialize(InetAddress.getByName(ip), HEOS_DEFAULT_PORT);
		JSONObject response = new JSONObject(client.send(heosCommand));
		client.close();
		verifyResponse(response);
		return response;
	}

	private void verifyResponse(JSONObject response) {
		if (!(response.getJSONObject(HEOS_PROTOCOLL).getString(HEOS_JSON_RESULT).equals("success"))) {
			throw new IllegalStateException("A communication error has been occured!");
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
