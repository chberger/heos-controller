package de.chberger.heos.unit.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import de.chberger.heos.device.HeosSpeaker;
import de.chberger.heos.device.management.api.HeosDeviceManager;
import de.chberger.heos.junit.weld.WeldJUnit4Runner;
import de.chberger.heos.speaker.types.PlayState;
import de.chberger.protocol.ssdp.UPNPDevice;
import de.chberger.protocol.ssdp.api.SSDPClient;
import de.chberger.protocol.ssdp.types.ServiceType;

/**
 * Unit tests for HeosDeviceManager
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(WeldJUnit4Runner.class)
public class HeosDeviceManagerSendCommandsTest {

	@Inject
	private HeosDeviceManager deviceManager;

	@Inject
	private SSDPClient client;

	private static HeosSpeaker testSpeaker;

	@Test
	public void a_openTelnetConnection() throws IOException {
		UPNPDevice device = client.discoverSingleDevice(1500, ServiceType.HEOS);
		Set<HeosSpeaker> speakers = deviceManager.getSpeakers(device);
		testSpeaker = speakers.iterator().next();
	}

	@Test
	public void playHeosSpeaker() {
		try {
			JSONObject response = deviceManager.send(testSpeaker.setPlayState(PlayState.PLAY), testSpeaker);
			assertTrue(response.getJSONObject("heos").getString("message")
					.equals(String.format("pid=%s&state=%s", testSpeaker.getPid(), PlayState.PLAY.getState())));
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void stopHeosSpeaker() {
		try {
			JSONObject response = deviceManager.send(testSpeaker.setPlayState(PlayState.STOP), testSpeaker);
			assertTrue(response.getJSONObject("heos").getString("message")
					.equals(String.format("pid=%s&state=%s", testSpeaker.getPid(), PlayState.STOP.getState())));

		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void z_closeTelnetConnection() throws IOException {
		deviceManager.closeAllConnections();
	}

}