package de.chberger.heos.main;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

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
		container.shutdown();
	}

	private void initalizeCDIContainer() {
		Weld weld = new Weld();
		container = weld.initialize();
	}	
}
