package controllers;

import java.util.ArrayList;

import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;

public class OrderInformationController implements UserControl {

	boolean unsafeExit = false;
	
	public void loadBranches()
	{
		ClientApp.ProtocolHandler.Invoke(RequestType.GetBranches, null, null, true);
		branchCombobox.setItems((ObservableList)(ClientApp.ProtocolHandler.GetResponse(RequestType.GetBranches)));
	}
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private DatePicker arrivalDate;

    @FXML
    private ComboBox<?> branchCombobox;

    @FXML
    private TextField cityText;
    
    @FXML
    private CheckBox useGreeting;
    
    @FXML
    private ToggleGroup deliveryMethod;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private TextArea greetingText;

    @FXML
    private Button nextBtn;

    @FXML
    private TextField streetAddressText;

    @FXML
    void nextBtn_Click(ActionEvent event) {
    	if(!isInputValid()) return;
    	
    	LoginController.windowControl.putPipe("orderInfo",
    		
    }
    
    private boolean isInputValid()
    {
    	StringBuilder errorNotification = new StringBuilder("");
    	boolean valid = true;
    	if(deliveryMethod.getSelectedToggle() != null)
    	{
    		errorNotification.append("Must select delivery method\n");
    		valid = false;
    	}
    	if(cityText.getText().equals("") || streetAddressText.getText().equals(""))
    	{
    		errorNotification.append("Address fields cannot be empty\n");
    		valid = false;
    	}
    	if(branchCombobox.getValue() == null)
    	{
    		errorNotification.append("Please select a branch\n");
    		valid = false;
    	}
    	if(useGreeting.isSelected() && greetingText.getText().equals(""))
    	{
    		errorNotification.append("Greeting cannot be empty\n");
    		valid = false;
    	}
    	if(arrivalDate.getValue() == null)
    	{
    		errorNotification.append("Please select arrival date\n");
    		valid = false;
    	}
    	
    	errorLabel.setText(errorNotification.toString());
    	return valid;
    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExit() {
		/*if(unsafeExit)
		{
			Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
			confirmAlert.setTitle("Leaving Order Information");
			confirmAlert.setContentText("Are you sure you want to cancel the order?");
			ButtonType yes = new ButtonType("Yes", ButtonData.YES);
			ButtonType no = new ButtonType("No",ButtonData.NO);
			confirmAlert.getDialogPane().getButtonTypes().add(yes);
			confirmAlert.getDialogPane().getButtonTypes().add(no);
			confirmAlert.showAndWait();
		}*/
		
	}

}
