package de.chberger.heos.device;

import de.chberger.heos.speaker.types.MuteState;
import de.chberger.heos.speaker.types.PlayState;
import de.chberger.heos.speaker.types.RepeatState;
import de.chberger.heos.speaker.types.ShuffleState;

public class HeosSpeaker {
	
	private String name;
	private long pid;
	private long gid;
	private String ip;
	private String model;
	private String version;
	private long lineout;
	private String network;
	
	public HeosSpeaker(String name, long pid, long gid, String ip, String model, String version, long lineout, String network) {
		super();
		this.name = name;
		this.pid = pid;
		this.gid = gid;
		this.ip = ip;
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
	
	public String getIp() {
		return ip;
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
	
	public String getPlayerInfo() {
		return String.format("heos://player/get_player_info?pid=%s", pid);
	}

	public String getPlayState() {
		return String.format("heos://player/get_play_state?pid=%s", pid);
	}

	public String setPlayState(PlayState pState) {
		return String.format("heos://player/set_play_state?pid=%s&state=%s", pid, pState.getState());
	}

	public String getNowPlayingMedia() {
		return String.format("heos://player/get_now_playing_media?pid=%s", pid);
	}

	public String getVolume() {
		return String.format("heos://player/get_volume?pid=%s", pid);
	}

	/**
	 * Set the volume level of the corresponding heos device
	 * 
	 * @param volLevel
	 *            Volume level (0 to 100)
	 * @return heos device response
	 */
	public String setVolume( short volLevel) {
		// TODO: input paramter check
		return String.format("heos://player/set_volume?pid=%s&level=%s", pid, volLevel);
	}

	public String volumeUp(short stepLevel) {
		return String.format("heos://player/volume_up?pid=%s&step=%s", pid, stepLevel);
	}

	public String volumeDown(short stepLevel) {
		return String.format("heos://player/volume_down?pid=%s&step=%s", pid, stepLevel);
	}

	public String getMute() {
		return String.format("heos://player/get_mute?pid=%s", pid);
	}

	public String setMute(MuteState mState) {
		return String.format("heos://player/set_mute?pid=%s&state=%s", pid, mState.getState());
	}

	public String toggleMute() {
		return String.format("heos://player/toggle_mute?pid=%s", pid);
	}

	public String getPlayMode() {
		return String.format("heos://player/get_play_mode?pid=%s", pid);
	}

	public String setPlayMode(RepeatState rState, ShuffleState sState) {
		return String.format("heos://player/set_play_mode?pid=%s&repeat=%s&shuffle=%s", pid,
				rState.getState(), sState.getState());
	}

	public String getQueue() {
		return String.format("heos://player/get_queue?pid=%s", pid);
	}

	public String playQueueItem(long qsid) {
		return String.format("heos://player/play_queue?pid=%s&qid=%s", pid, qsid);
	}

	public String removeItemFromQueue(long qid) {
		return String.format("heos://player/remove_from_queue?pid=%s&qid=%s", pid, qid);
	}

	public String saveQueueAsPlaylist( String playlistName) {
		return String.format("heos://player/save_queue?pid=%s&name=%s", pid, playlistName);
	}

	public String clearQueue() {
		return String.format("heos://player/clear_queue?pid=%s", pid);
	}

	public String playNext() {
		return String.format("heos://player/play_next?pid=%s", pid);
	}

	public String playPrevious() {
		return String.format("heos://player/play_previous?pid=%s", pid);
	}


	@Override
	public String toString() {
		return "Speaker{" + "name='" + name + '\'' + ", pid='" + pid + '\'' + ", gid='" + gid + '\''
				+ ", model='" + model + '\'' + ", version='" + version + '\'' + ", lineout='" + lineout + '\''
				+ ", network='" + network + '\'' + '}';
	}

}
