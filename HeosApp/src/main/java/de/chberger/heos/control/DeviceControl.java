package de.chberger.heos.control;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import de.chberger.heos.devices.Speaker;
import de.chberger.heos.devices.types.MuteState;
import de.chberger.heos.devices.types.PlayState;
import de.chberger.heos.devices.types.RepeatState;
import de.chberger.heos.devices.types.ShuffleState;
import de.chberger.protocoll.ssdp.types.ServiceType;
import de.chberger.protocoll.ssdp.types.UPNPDevice;
import de.chberger.protocoll.telnet.TelnetClient;

public class DeviceControl {

	private static final Logger log = Logger.getLogger(DeviceControl.class.getName());
	private static final int DEFAULT_PORT_HEOS_DEVICE = 1255;

	private static TelnetClient client;

	public static Set<Speaker> registerDevice(UPNPDevice device) {
		Set<Speaker> speakers = new HashSet<Speaker>();

		if (device.getServiceType().equals(ServiceType.HEOS.getUrn())) {
			// Define telnet connection
			client = new TelnetClient(device.getIPAddress(), DEFAULT_PORT_HEOS_DEVICE);

			JSONObject response = new JSONObject(client.send("heos://player/get_players"));
			if (response.getJSONObject("heos").getString("result").equals("success")) {

				final JSONArray players = response.getJSONArray("payload");
				for (int i = 0; i < players.length(); i++) {
					JSONObject player = players.getJSONObject(i);
					Speaker speaker = new Speaker(player.getString("name"), player.getLong("pid"),
							player.getString("model"), player.getString("version"), player.getLong("lineout"),
							player.getString("network"), device);
					speakers.add(speaker);
				}
			} else {
				throw new RuntimeException(String.format("Unable to determine speakers for UPnP device %s!", device));
			}

		} else {
			throw new RuntimeException(
					String.format("Unable to hanlde device with service type %s! Only heos devices are permitted!",
							device.getServiceType()));
		}
		return Collections.unmodifiableSet(speakers);

	}

	public static void unregsiterDevice() throws IOException {
		if (client != null) {
			client.close();
		}
	}

	public static String getPlayerInfo(long pid) {
		return client.send(String.format("heos://player/get_player_info?pid=%s", pid));
	}

	public static String getPlayState(long pid) {
		return client.send(String.format("heos://player/get_play_state?pid=%s", pid));
	}

	public static String setPlayState(long pid, PlayState pState) {
		return client.send(String.format("heos://player/set_play_state?pid=%s&state=%s", pid, pState.getState()));
	}

	public static String getNowPlayingMedia(long pid) {
		return client.send(String.format("heos://player/get_now_playing_media?pid=%s", pid));
	}

	public static String getVolume(long pid) {
		return client.send(String.format("heos://player/get_volume?pid=%s", pid));
	}

	/**
	 * Set the volume level of the corresponding heos device
	 * 
	 * @param volLevel
	 *            Volume level (0 to 100)
	 * @return heos device response
	 */
	public static String setVolume(long pid, short volLevel) {
		// TODO: input paramter check
		return client.send(String.format("heos://player/set_volume?pid=%s&level=%s", pid, volLevel));
	}

	public static String volumeUp(long pid, short stepLevel) {
		return client.send(String.format("heos://player/volume_up?pid=%s&step=%s", pid, stepLevel));
	}

	public static String volumeDown(long pid, short stepLevel) {
		return client.send(String.format("heos://player/volume_down?pid=%s&step=%s", pid, stepLevel));
	}

	public static String getMute(long pid) {
		return client.send(String.format("heos://player/get_mute?pid=%s", pid));
	}

	public static String setMute(long pid, MuteState mState) {
		return client.send(String.format("heos://player/set_mute?pid=%s&state=%s", pid, mState.getState()));
	}

	public static String toggleMute(long pid) {
		return client.send(String.format("heos://player/toggle_mute?pid=%s", pid));
	}

	public static String getPlayMode(long pid) {
		return client.send(String.format("heos://player/get_play_mode?pid=%s", pid));
	}

	public static String setPlayMode(long pid, RepeatState rState, ShuffleState sState) {
		return client.send(String.format("heos://player/set_play_mode?pid=%s&repeat=%s&shuffle=%s", pid,
				rState.getState(), sState.getState()));
	}

	public static String getQueue(long pid) {
		return client.send(String.format("heos://player/get_queue?pid=%s", pid));
	}

	public static String playQueueItem(long pid, long qsid) {
		return client.send(String.format("heos://player/play_queue?pid=%s&qid=%s", pid, qsid));
	}

	public static String removeItemFromQueue(long pid, long qid) {
		return client.send(String.format("heos://player/remove_from_queue?pid=%s&qid=%s", pid, qid));
	}

	public static String saveQueueAsPlaylist(long pid, String playlistName) {
		return client.send(String.format("heos://player/save_queue?pid=%s&name=%s", pid, playlistName));
	}

	public static String clearQueue(long pid) {
		return client.send(String.format("heos://player/clear_queue?pid=%s", pid));
	}

	public static String playNext(long pid) {
		return client.send(String.format("heos://player/play_next?pid=%s", pid));
	}

	public static String playPrevious(long pid) {
		return client.send(String.format("heos://player/play_previous?pid=%s", pid));
	}

}
