package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import Entities.CatalogType;
import controllers.CustomerFrameController;
import controllers.CatalogViewerController;
public class CustomerHomePageController {
CustomerFrameController customerFrameController;
CatalogViewerController CatalogViewerController;
    @FXML
    private Button castumBtn;

    @FXML
    private Button prePefineBtn;

    @FXML
    void customPressed(ActionEvent event) {
    	CatalogViewerController.setCatalogType(CatalogType.custom);
    customerFrameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
    
    }

    @FXML
    void preDefinePressed(ActionEvent event) {
    	CatalogViewerController.setCatalogType(CatalogType.pre_define);
    	customerFrameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
    }

	public void setController(CustomerFrameController customerFrameController) {
		this.customerFrameController=customerFrameController;
	}
	

}
