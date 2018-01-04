package de.chberger.heos.device.control;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.chberger.heos.junit.weld.WeldJUnit4Runner;
import de.chberger.protocoll.ssdp.UPNPDevice;
import de.chberger.protocoll.ssdp.api.SSDPClient;
import de.chberger.protocoll.ssdp.types.ServiceType;

/**
 * Unit test for RemoteControl
 */
@RunWith(WeldJUnit4Runner.class)
public class RemoteControlTest {

	@Inject
	private RemoteControl control;
	
	@Inject
	private SSDPClient client;

	@Test
	public void testRemoteControlInjection() {
		assertNotNull(control);
	}
	
	@Test
	public void testDiscoverAnyUPNPDevice() {
		Set<UPNPDevice> devices;
		try {
			devices = client.discover(ServiceType.HEOS);
			assertTrue(devices.size()>0);
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}

}