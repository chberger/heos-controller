package de.chberger.heos.main;

import java.io.IOException;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import de.chberger.heos.device.control.SpeakerRegistry;
import de.chberger.protocoll.telnet.api.TelnetClient;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main entrance class which bootstraps the runtime environment.
 * 
 * @author chberger
 *
 */
public class HeosApp extends Application {

	private WeldContainer container;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		initalizeCDIContainer();
		// trigger service to load GUI elements and properties
		StageInitService stageInitService = container.select(StageInitService.class).get();
		stageInitService.init(stage);
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		closeClientConnections();
		container.shutdown();
	}

	private void initalizeCDIContainer() {
		Weld weld = new Weld();
		container = weld.initialize();
	}
	
	private void closeClientConnections() throws IOException {
		SpeakerRegistry registry = container.select(SpeakerRegistry.class).get();
		for (TelnetClient client : registry.getAllTelnetClients()) {
			if (client != null) {
				client.close();
			}
		}
	}
	
	
}
