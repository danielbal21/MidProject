package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ManagerFrameController implements IContainable{

    @FXML
    private AnchorPane controlContainer;

	@Override
	public AnchorPane getControlContainer() {
		return controlContainer;
	}


}
