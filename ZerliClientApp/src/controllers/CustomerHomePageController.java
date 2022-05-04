package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomerHomePageController {
	
    @FXML
    private Button castumBtn;

    @FXML
    private Button prePefineBtn;

    @FXML
    void customPressed(ActionEvent event) {
    	LoginController.windowControl.frameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
    }

    @FXML
    void preDefinePressed(ActionEvent event) {
    	LoginController.windowControl.frameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
    }
    

}
