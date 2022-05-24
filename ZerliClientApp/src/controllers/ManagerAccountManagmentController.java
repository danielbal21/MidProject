package controllers;

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
    	System.out.println("2");
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
