package controllers;

import javafx.stage.Stage;

public class WindowControl {

	public IContainable frameController;
	
	public static Stage stage;
	
	public WindowControl(IContainable frameController) {
		this.frameController = frameController;
	}

}
