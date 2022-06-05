/*
 * 
 */
package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import Entities.CatalogType;

/**
 *The Class CustomerHomePageController is the controller part of the Customer GUI.
 *The Class give the ability to the Customer to choose the catalog type that he want to get access
 *The class implement user control interface to be able to insert into frame users GUI
 */
public class CustomerHomePageController implements UserControl{

    /** The costume button. */
    @FXML
    private Button castumBtn;

    /** The pre-define button. */
    @FXML
    private Button prePefineBtn;
    
    /** The private button. */
    @FXML
    private Button privateBtn;

    /**
     * Custom pressed.
     * When the custom pressed go to the custom catalog and import the custom items 
     * @param event the event
     */
    @FXML
    void customPressed(ActionEvent event) {
    	LoginController.windowControl.putPipe("catalog", CatalogType.custom);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCatalogViewer.fxml");
    }

    /**
     * Pre-define pressed.
     * When the Pre-define pressed go to the Pre-define catalog and import the Pre-define items 
     * @param event the event
     */
    @FXML
    void preDefinePressed(ActionEvent event) {
    	LoginController.windowControl.putPipe("catalog", CatalogType.pre_define);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCatalogViewer.fxml");
    }
    
    

    /**
     * Private pressed.
     * When the newItem pressed go to the newItem catalog and import all the items 
     * @param event the event
     */
    @FXML
    void privatePressed(ActionEvent event) {
    	LoginController.windowControl.putPipe("catalog", CatalogType.new_item);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCatalogViewer.fxml");
    }
    
    @FXML
    void helpBtnPressed(ActionEvent event) {
		Alert confirmAlert = new Alert(AlertType.NONE);
		confirmAlert.setTitle("Help - Catalog Selection");
		confirmAlert.setContentText("There are 3 buttons ahead:\n choose Pre-defined products if you seek already made for you items"
				+ "\nchoose Single item if you seek more elemental items that can be made into products"
				+ "\nchoose build your private products if you seek to construct a product from both catalog mentioned above");
		ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
		confirmAlert.getDialogPane().getButtonTypes().add(ok);
		confirmAlert.showAndWait();
    }
	/**
	 * On enter.
	 */
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
	}

	/**
	 * On exit.
	 */
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}
}