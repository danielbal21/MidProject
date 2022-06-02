package controllers;

import Entities.CustomerInfo;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerAccountInfoController implements UserControl{

    @FXML
    private TextField ID;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private TextField creditCard;

    @FXML
    private TextField cvv;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField monthExp;

    @FXML
    private Label newCustomerLabel;

    @FXML
    private TextField phone;

    @FXML
    private TextField yearExp;

    @FXML
    private TextField zerliCoins;

    @FXML
    void backPressed(ActionEvent event) {
		LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerHomePage.fxml");
    }

	@Override
	public void onEnter() {
		ClientApp.ProtocolHandler.Invoke(RequestType.GetCustomerInfo, null,null, true);
		CustomerInfo customer =(CustomerInfo) ClientApp.ProtocolHandler.GetResponse(RequestType.GetCustomerInfo);
		firstName.setText(customer.getFirstName());
		lastName.setText(customer.getLastName());
		ID.setText(customer.getID());
		email.setText(customer.getEmail());
		phone.setText(customer.getPhone());
		creditCard.setText(customer.getCreditCard());
		cvv.setText(customer.getCvv());
		monthExp.setText(customer.getExpMonth());
		yearExp.setText(customer.getExpYear());
		zerliCoins.setText(customer.getZerliCoins());
		newCustomerLabel.setVisible(customer.isNewCustomer());
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}

}
