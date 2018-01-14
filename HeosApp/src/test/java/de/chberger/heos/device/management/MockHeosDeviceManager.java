package de.chberger.heos.device.management;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import de.chberger.heos.device.management.api.TelnetClientManager;

@Alternative
public class MockHeosDeviceManager extends HeosDeviceManagerImpl {
	
	@Inject	
	private void setTelnetClientManager(TelnetClientManager clientManager) {
		this.tcManager = clientManager;
	}
}
