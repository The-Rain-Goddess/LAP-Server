/**
 * 
 */
package com.rain.app.server.redux.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Ryan May
 *
 */
public class MainWindow extends Application {
	private static final int WINDOW_MIN_HEIGHT = 700, WINDOW_MIN_WIDTH = 1000;

	/**
	 * 
	 */
	public MainWindow() {
		// TODO Auto-generated constructor stub
	}
	
	public void begin(String[] args){
		launch(args);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage root) throws Exception {
		AnchorPane mainComponentPane = new AnchorPane();
		Scene mainScene = new Scene(mainComponentPane, WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT);
		root.setScene(mainScene);
		root.show();
		
	}

}
