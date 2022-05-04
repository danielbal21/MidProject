package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
<<<<<<< HEAD

=======
import Entities.CatalogType;
import controllers.CustomerFrameController;
import controllers.CatalogViewerController;
>>>>>>> refs/heads/Ido
public class CustomerHomePageController {
<<<<<<< HEAD
	
=======
CustomerFrameController customerFrameController;
CatalogViewerController CatalogViewerController;
>>>>>>> refs/heads/Ido
    @FXML
    private Button castumBtn;

    @FXML
    private Button prePefineBtn;

    @FXML
    void customPressed(ActionEvent event) {
<<<<<<< HEAD
    	LoginController.windowControl.frameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
=======
    	CatalogViewerController.setCatalogType(CatalogType.custom);
    customerFrameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
    
>>>>>>> refs/heads/Ido
    }

    @FXML
    void preDefinePressed(ActionEvent event) {
<<<<<<< HEAD
    	LoginController.windowControl.frameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
=======
    	CatalogViewerController.setCatalogType(CatalogType.pre_define);
    	customerFrameController.setControlContainer("/gui/usercontrols/CustomerCatalogViewer.fxml");
>>>>>>> refs/heads/Ido
    }
<<<<<<< HEAD
    
=======

	public void setController(CustomerFrameController customerFrameController) {
		this.customerFrameController=customerFrameController;
	}
	
>>>>>>> refs/heads/Ido

}
