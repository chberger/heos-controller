package de.chberger.heos.devices.types;

public enum ShuffleState {

	ON("on"),
	OFF("off");
	
	private String state;
	
	private ShuffleState(String state) {
		this.state=state;
	}

	public String getState() {
		return state;
	}
}
