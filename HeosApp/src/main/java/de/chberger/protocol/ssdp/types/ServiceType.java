package de.chberger.protocol.ssdp.types;

public enum ServiceType {
	
	DEFAULT("ssdp:all"),
	HEOS("urn:schemas-denon-com:device:ACT-Denon:1");
	
	private String urn;
	
	private ServiceType(String urn) {
		this.urn = urn;
	}
	
	public String getUrn() {
		return this.urn;
	}

}
