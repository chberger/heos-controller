package de.chberger.heos.device;

import javax.inject.Inject;

import org.json.JSONObject;

import de.chberger.heos.device.control.SpeakerRegistry;
import de.chberger.heos.device.types.MuteState;
import de.chberger.heos.device.types.PlayState;
import de.chberger.heos.device.types.RepeatState;
import de.chberger.heos.device.types.ShuffleState;
import de.chberger.protocoll.telnet.api.TelnetClient;

public class Speaker {
	
	@Inject
	private SpeakerRegistry registry;

	private String name;
	private long pid;
	private long gid;
	private String model;
	private String version;
	private long lineout;
	private String network;
	
	public Speaker(String name, long pid, String model, String version, long lineout, String network) {
		super();
		this.name = name;
		this.pid = pid;
		this.gid = -1;
		this.model = model;
		this.version = version;
		this.lineout = lineout;
		this.network = network;
	}

	public String getName() {
		return name;
	}

	public long getPid() {
		return pid;
	}

	public long getGid() {
		return gid;
	}

	public String getModel() {
		return model;
	}

	public String getVersion() {
		return version;
	}

	public long getLineout() {
		return lineout;
	}

	public String getNetwork() {
		return network;
	}
	
	private TelnetClient getTelnetClient() {
		return registry.getAssocTelnetClient(this);
	}
	
	public JSONObject getPlayerInfo() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/get_player_info?pid=%s", pid)));
	}

	public JSONObject getPlayState() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/get_play_state?pid=%s", pid)));
	}

	public JSONObject setPlayState(PlayState pState) {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/set_play_state?pid=%s&state=%s", pid, pState.getState())));
	}

	public JSONObject getNowPlayingMedia() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/get_now_playing_media?pid=%s", pid)));
	}

	public JSONObject getVolume() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/get_volume?pid=%s", pid)));
	}

	/**
	 * Set the volume level of the corresponding heos device
	 * 
	 * @param volLevel
	 *            Volume level (0 to 100)
	 * @return heos device response
	 */
	public JSONObject setVolume( short volLevel) {
		// TODO: input paramter check
		return new JSONObject(getTelnetClient().send(String.format("heos://player/set_volume?pid=%s&level=%s", pid, volLevel)));
	}

	public JSONObject volumeUp(short stepLevel) {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/volume_up?pid=%s&step=%s", pid, stepLevel)));
	}

	public JSONObject volumeDown(short stepLevel) {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/volume_down?pid=%s&step=%s", pid, stepLevel)));
	}

	public JSONObject getMute() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/get_mute?pid=%s", pid)));
	}

	public JSONObject setMute(MuteState mState) {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/set_mute?pid=%s&state=%s", pid, mState.getState())));
	}

	public JSONObject toggleMute() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/toggle_mute?pid=%s", pid)));
	}

	public JSONObject getPlayMode() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/get_play_mode?pid=%s", pid)));
	}

	public JSONObject setPlayMode(RepeatState rState, ShuffleState sState) {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/set_play_mode?pid=%s&repeat=%s&shuffle=%s", pid,
				rState.getState(), sState.getState())));
	}

	public JSONObject getQueue() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/get_queue?pid=%s", pid)));
	}

	public JSONObject playQueueItem(long qsid) {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/play_queue?pid=%s&qid=%s", pid, qsid)));
	}

	public JSONObject removeItemFromQueue(long qid) {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/remove_from_queue?pid=%s&qid=%s", pid, qid)));
	}

	public JSONObject saveQueueAsPlaylist( String playlistName) {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/save_queue?pid=%s&name=%s", pid, playlistName)));
	}

	public JSONObject clearQueue() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/clear_queue?pid=%s", pid)));
	}

	public JSONObject playNext() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/play_next?pid=%s", pid)));
	}

	public JSONObject playPrevious() {
		return new JSONObject(getTelnetClient().send(String.format("heos://player/play_previous?pid=%s", pid)));
	}


	@Override
	public String toString() {
		return "Speaker{" + "name='" + name + '\'' + ", pid='" + pid + '\'' + ", gid='" + gid + '\''
				+ ", model='" + model + '\'' + ", version='" + version + '\'' + ", lineout='" + lineout + '\''
				+ ", network='" + network + '\'' + '}';
	}

}
