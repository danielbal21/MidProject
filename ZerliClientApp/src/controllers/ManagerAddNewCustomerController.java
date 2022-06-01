package controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import Entities.NotificationInTable;
import Entities.PendingClientInfo;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManagerAddNewCustomerController implements UserControl{

    @FXML
    private Label addError;
    
	@FXML
    private AnchorPane ClientInfoWindow;
	
    @FXML
    private Label searchErrorLBl;

    @FXML
    private TextField IDSearch;

    @FXML
    private TextField IDTxt;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private TextField cardNumTxt1;

    @FXML
    private TextField cardNumTxt2;

    @FXML
    private TextField cardNumTxt3;

    @FXML
    private TextField cardNumTxt4;

    @FXML
    private AnchorPane creditCardDialog;

    @FXML
    private TextField cvvText;

    @FXML
    private TextField emailTxt;

    @FXML
    private ComboBox<String> expMonthCB;

    @FXML
    private TextField firstNameTxt;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField userNameTxt;

    @FXML
    private ComboBox<String> yearCB;
    
    PendingClientInfo pendingInfo;

    @FXML
    void SearchPressed(MouseEvent event) {
		ClientInfoWindow.setVisible(false);

    	String IDtxt = IDSearch.getText();
    	
    	if(IDtxt.length() == 0 ) {
    		searchErrorLBl.setText("Please Enter ID");
    		return;
    	}
    	else if(IDtxt.length()!= 9 ) {
    		searchErrorLBl.setText("Invalid ID");
    		return;
    	}
    	
    	searchErrorLBl.setText("");
    	ClientApp.ProtocolHandler.Invoke(RequestType.GetPendingClient, null, IDtxt, true);
    	pendingInfo = (PendingClientInfo)ClientApp.ProtocolHandler.GetResponse(RequestType.GetPendingClient);
    	if(pendingInfo == null) {
    		searchErrorLBl.setText("Customer already registered");
    		searchErrorLBl.setVisible(true);
    	}
    	else { 
    		ClientInfoWindow.setVisible(true);
    		IDTxt.setText(pendingInfo.getID());
    		firstNameTxt.setText(pendingInfo.getFirstName());
    		lastNameTxt.setText(pendingInfo.getLastName());
    		phoneTxt.setText(pendingInfo.getPhone());
    		emailTxt.setText(pendingInfo.getEmail());
    		userNameTxt.setText(pendingInfo.getUserID());
    	}
    }
    
    @FXML
    void addCustomerPressed(MouseEvent event) {
    	
    	if(!validateInput()) return;
    	else {
    		pendingInfo.setCreditPhrases(new String[] {cardNumTxt1.getText(),cardNumTxt2.getText(),cardNumTxt3.getText(),cardNumTxt4.getText()});
    		pendingInfo.setCVV(cvvText.getText());
    		pendingInfo.setExpirationMonth(expMonthCB.getValue());
    		pendingInfo.setExpirationYear(yearCB.getValue());
    		pendingInfo.setPassword(passwordTxt.getText());
    		ClientApp.ProtocolHandler.Invoke(RequestType.RegisterClient, pendingInfo, pendingInfo.getUserID(), false);
    		NotificationInTable notification = new NotificationInTable();
    		notification.setTo(pendingInfo.getUserID());
    		notification.setFrom("Manager");
    		notification.setContent("Welcome to Zerli online Shop\nPlease receive 20% off on first purchase coupon");
    		ClientApp.ProtocolHandler.Invoke(RequestType.SendNotification,notification,null,false);
    		Alert confirmAlert = new Alert(AlertType.NONE);
    		confirmAlert.setTitle("Add new customer Information");
    		confirmAlert.setContentText("Customer with ID " + pendingInfo.getID() + " added Successfully\n"
    				+ "Message had been send to customer");
    		ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
    		confirmAlert.getDialogPane().getButtonTypes().add(ok);
    		Optional<ButtonType> result = confirmAlert.showAndWait();
    		result.ifPresent(response -> { 
    			if(response == ok) {
    				onEnter();
    			}
    		});
    	}
    }

	private boolean validateInput() {
		StringBuilder error = new StringBuilder("");
    	boolean valid = true;
    	
    	if(passwordTxt.getText().equals("") )
    	{
    		error.append("Password field missing\n");
    		valid = false;
    	}
    	else if(passwordTxt.getText().length() < 4 ) {
    		error.append("Password need 4 characters minimum\n");
    		valid = false;
    	}
    	
    	var creds = new String[] {cardNumTxt1.getText(),cardNumTxt2.getText(),cardNumTxt3.getText(),cardNumTxt4.getText()};
    	for(String s : creds)
    	{
    		if(s.length() != 4)
    		{
    			error.append("Please enter valid credit card\n");
    			valid = false;
    			break;
    		}
    		try
    		{
    			Integer.parseInt(s);
    		}
    		catch(NumberFormatException e)
    		{
    			error.append("Please enter valid credit card\n");
    			valid = false;
    			break;
    		}
    	}
    
    	
    	if(yearCB.getValue() == null || expMonthCB.getValue() == null)
    	{
    		error.append("Please enter a valid expiration date\n");
    		valid = false;
    	}
    	else {
    		int year= Integer.parseInt(yearCB.getValue());
    		int month = Integer.parseInt(expMonthCB.getValue());
    		if(LocalDateTime.now().getYear() == year) {
    			if(LocalDateTime.now().getMonthValue() > month) {
    				error.append("Please enter a valid expiration date\n");
    				valid = false;
    			}
    		}
    	}
    	
    	if(cvvText.getText().equals(""))
    	{
    		error.append("CVV Cannot be empty\n");
    		valid = false;
    	}
    	else if(cvvText.getText().length() != 3 ) {
    		error.append("CVV must be 3 digits\n");
    		valid = false;
    	}
    	
        addError.setText(error.toString());
    	
    	return valid;
	}
	

    @FXML
    void backPressed(ActionEvent event) {
		LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerAccountManagment.fxml");
    }

	@Override
	public void onEnter() {
		ClientInfoWindow.setVisible(false);
		searchErrorLBl.setText("");
		IDSearch.setText("");
		addError.setText("");
		
		var currentYear = LocalDateTime.now().toLocalDate().getYear();
		var years = new ArrayList<String>();
		var months = new ArrayList<String>();
		for(var i = currentYear;i < currentYear + 12;i++)
			years.add(i + "");
		for(var i = 1;i<=12;i++)
			months.add((i+"").length() == 1 ? ("0"+ i) : i + "");
		yearCB.setItems(FXCollections.observableArrayList(years));
		expMonthCB.setItems(FXCollections.observableArrayList(months));
	}

	@Override
	public void onExit() {
		searchErrorLBl.setText("");
		IDSearch.setText("");
		addError.setText("");
		passwordTxt.setText("");
		cardNumTxt1.setText("");
		cardNumTxt2.setText("");
		cardNumTxt3.setText("");
		cardNumTxt4.setText("");
		cvvText.setText("");
		expMonthCB.setValue(null);
		yearCB.setValue(null);
	}  
}
