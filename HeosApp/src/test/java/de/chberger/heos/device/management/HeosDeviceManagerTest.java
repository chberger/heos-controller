package de.chberger.heos.device.management;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.chberger.heos.device.HeosSpeaker;
import de.chberger.heos.device.management.HeosDeviceManager;
import de.chberger.heos.junit.weld.WeldJUnit4Runner;
import de.chberger.protocoll.ssdp.UPNPDevice;
import de.chberger.protocoll.ssdp.api.SSDPClient;
import de.chberger.protocoll.ssdp.types.ServiceType;

/**
 * Unit test for RemoteControl
 */
@RunWith(WeldJUnit4Runner.class)
public class HeosDeviceManagerTest {

	@Inject
	private HeosDeviceManager deviceManager;
	
	@Inject
	private SSDPClient client;

	@Test
	public void testInjection() {
		assertNotNull(deviceManager);
		assertNotNull(client);
	}
	
	@Test
	public void testDiscoverAllHeosDevices() {
		Set<UPNPDevice> devices;
		try {
			devices = client.discover(ServiceType.HEOS);
			assertTrue(devices.size()>0);
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getSingleUPNPDevice() {
		UPNPDevice device;
		try {
			device = client.discoverSingleDevice(1500, ServiceType.HEOS);
			Set<HeosSpeaker> speakers = deviceManager.getSpeakers(device);
			assertTrue(speakers.size()>0);
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void getHeosSpeakers() {
		UPNPDevice device;
		try {
			device = client.discoverSingleDevice(1500, ServiceType.HEOS);
			Set<HeosSpeaker> speakers = deviceManager.getSpeakers(device);
			for (HeosSpeaker heosSpeaker : speakers) {
				assertTrue(heosSpeaker.getPid()!=0);
			}
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}
	

}