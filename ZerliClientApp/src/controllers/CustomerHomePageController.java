package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import controllers.CustomerFrameController;

public class CustomerHomePageController {
CustomerFrameController customerFrameController;
    @FXML
    private Button castumBtn;

    @FXML
    private Button prePefineBtn;

    @FXML
    void customPressed(ActionEvent event) {
    FXMLLoader loader=new FXMLLoader();
    customerFrameController.setControlContainer("CustomerCatalogViewer");
    }

    @FXML
    void preDefinePressed(ActionEvent event) {
    	customerFrameController.setControlContainer("CustomerCatalogViewer");
    }

	public void setController(CustomerFrameController customerFrameController) {
		this.customerFrameController=customerFrameController;
	
	}


}
