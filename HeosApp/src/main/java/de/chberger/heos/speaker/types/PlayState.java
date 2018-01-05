package de.chberger.heos.speaker.types;

public enum PlayState {

	PLAY("play"),
	PAUSE("pause"),
	STOP("stop");
	
	private String state;
	
	private PlayState(String state) {
		this.state=state;
	}

	public String getState() {
		return state;
	}
}
