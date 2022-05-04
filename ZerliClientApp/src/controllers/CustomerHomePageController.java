package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import Entities.CatalogType;
import controllers.CustomerFrameController;
import controllers.CatalogViewerController;

public class CustomerHomePageController {

    @FXML
    private Button castumBtn;

    @FXML
    private Button prePefineBtn;

    @FXML
    void customPressed(ActionEvent event) {
    	LoginController.windowControl.frameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
    	//Fix global user control access
    	//CatalogViewerController.setCatalogType(CatalogType.custom);
    }

    @FXML
    void preDefinePressed(ActionEvent event) {
    	LoginController.windowControl.frameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
    	//Fix global user control access
    	//CatalogViewerController.setCatalogType(CatalogType.pre_define);
    }
}
