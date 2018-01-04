package de.chberger.heos.device.control;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;

import de.chberger.heos.device.Speaker;
import de.chberger.protocoll.ssdp.UPNPDevice;
import de.chberger.protocoll.ssdp.types.ServiceType;
import de.chberger.protocoll.telnet.api.TelnetClient;

public class RemoteControl {

	private static final int DEFAULT_PORT_HEOS_DEVICE = 1255;

	@Inject
	private TelnetClient client;

	@Inject
	private SpeakerRegistry registry;

	public Set<Speaker> getHeosSpeakers(UPNPDevice device) throws UnknownHostException {

		if (device.getServiceType().equals(ServiceType.HEOS.getUrn())) {

			client.initialize(InetAddress.getByName(device.getIPAddress()), DEFAULT_PORT_HEOS_DEVICE);
			JSONObject response = getPlayers();
			Set<Speaker> speakers = parseJSON(response);
			registry.addSpeakers(speakers, client);
			return speakers;

		} else {
			throw new RuntimeException(String.format(
					"Unable to hanlde device with service type <%s>! Only Heos devices <%s> are permitted!",
					device.getServiceType(), ServiceType.HEOS.getUrn()));
		}
	}

	private JSONObject getPlayers() {
		return new JSONObject(client.send("heos://player/get_players"));
	}

	private Set<Speaker> parseJSON(JSONObject response) {
		Set<Speaker> speakers = new HashSet<Speaker>();
		if (response.getJSONObject("heos").getString("result").equals("success")) {

			final JSONArray players = response.getJSONArray("payload");
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);
				Speaker speaker = new Speaker(player.getString("name"), player.getLong("pid"),
						player.getString("model"), player.getString("version"), player.getLong("lineout"),
						player.getString("network"));
				speakers.add(speaker);
			}
		} else {
			throw new RuntimeException(String.format("A communication error has been occured!"));
		}
		return Collections.unmodifiableSet(speakers);
	}

}
