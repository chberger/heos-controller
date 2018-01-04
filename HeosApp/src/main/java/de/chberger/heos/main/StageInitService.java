package de.chberger.heos.main;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.inject.Inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This service is responsible for loading initial GUI elements and properties.
 * 
 * @author chberger
 *
 */
public class StageInitService {

	private static final String APP_NAME = "HeosApp";
	private static final String META_INF = "META-INF";
	private static final String LANGUAGES = "languages";

	@Inject
	private Logger logger;

	/**
	 * Initializes the root GUI elements of the HeosApp. This method has to be
	 * called during the application startup.
	 * 
	 * @param stage
	 *            root container of all GUI elements
	 * @throws IOException
	 */
	public void init(Stage stage) throws IOException {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		// load language properties
		ResourceBundle resources = ResourceBundle.getBundle(META_INF + "/" + LANGUAGES + "/" + APP_NAME);
		// load root gui element
		VBox root = FXMLLoader.<VBox>load(systemClassLoader.getResource(META_INF + "/layout.fxml"), resources);
		Scene scene = new Scene(root);
		stage.setTitle(APP_NAME);
		stage.setScene(scene);
		stage.show();
		logger.info(String.format("%s has been started successfully!", APP_NAME));
	}

}
