package de.chberger.heos.devices.types;

public enum RepeatState {

	ON_ALL("on_all"),
	ON_ONE("on_one"),
	OFF("off");
	
	private String state;
	
	private RepeatState(String state) {
		this.state=state;
	}

	public String getState() {
		return state;
	}
}
