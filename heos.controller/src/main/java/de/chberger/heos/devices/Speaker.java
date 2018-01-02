package de.chberger.heos.devices;

import de.chberger.protocoll.ssdp.types.UPNPDevice;

public class Speaker extends UPNPDevice {

	private String name;
	private long pid;
	private long gid;
	private String model;
	private String version;
	private long lineout;
	private String network;

	public Speaker(String name, long pid, String model, String version, long lineout, String network,
			UPNPDevice device) {
		super(device.getIPAddress(), device.getDescriptionUrl(), device.getServer(), device.getServiceType(),
				device.getUSN());
		this.name = name;
		this.pid = pid;
		this.gid = -1;
		this.model = model;
		this.version = version;
		this.lineout = lineout;
		this.network = network;
	}

	public Speaker(String name, long pid, String model, String version, long lineout, String network, String ip,
			String descriptionUrl, String server, String serviceType, String usn) {
		super(ip, descriptionUrl, server, serviceType, usn);
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

	@Override
	public String toString() {
		return super.toString() + " Speaker{" + "name='" + name + '\'' + ", pid='" + pid + '\'' + ", gid='" + gid + '\''
				+ ", model='" + model + '\'' + ", version='" + version + '\'' + ", lineout='" + lineout + '\''
				+ ", network='" + network + '\'' + '}';
	}

}
