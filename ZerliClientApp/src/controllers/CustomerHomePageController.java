package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import Entities.CatalogType;
import controllers.CustomerFrameController;
import controllers.CatalogViewerController;

public class CustomerHomePageController implements UserControl{

    @FXML
    private Button castumBtn;

    @FXML
    private Button prePefineBtn;

    @FXML
    void customPressed(ActionEvent event) {
    	LoginController.windowControl.putPipe("catalog", CatalogType.custom);
    	//LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCatalogViewer.fxml");
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerOrderInformation.fxml");
    }

    @FXML
    void preDefinePressed(ActionEvent event) {
    	LoginController.windowControl.putPipe("catalog", CatalogType.pre_define);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCatalogViewer.fxml");
    	
    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}
}
