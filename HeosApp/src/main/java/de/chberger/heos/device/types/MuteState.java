package de.chberger.heos.device.types;

public enum MuteState {
	
	ON("on"),
	OFF("off");
	
	private String state;
	
	private MuteState(String state) {
		this.state=state;
	}

	public String getState() {
		return state;
	}
}
