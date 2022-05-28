package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManagerAccountManagmentController implements UserControl{

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private ImageView addNewAcoount;

    @FXML
    private ImageView editAccounts;

    @FXML
    void addNewAcoountPressed(MouseEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerAddNewCustomer.fxml");
    }

    @FXML
    void editAccountsPressed(MouseEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerEditAccounts.fxml");
    }
    
    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerHomePage.fxml");

    }

	@Override
	public void onEnter() {
	}

	@Override
	public void onExit() {
	}

}
