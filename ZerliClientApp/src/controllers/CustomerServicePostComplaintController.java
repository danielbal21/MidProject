package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import Entities.Complaint;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerServicePostComplaintController implements UserControl {

    @FXML
    private TextArea feedBackText;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private ComboBox<String> branchField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField userIDField;

    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    }

    @FXML
    void postPress(ActionEvent event) {
    	//check if combo box value selected
    	try {
    			branchField.getValue().equals("");
    		}catch(NullPointerException e)
    		{
    			errorLabel.setVisible(true);
    			errorLabel.setText("Please select branch");
    			return;
    		}
    	//
    	Complaint complaint = new Complaint();
    	complaint.setIDnumber(userIDField.getText());
    	complaint.setBranch(branchField.getValue());
    	complaint.setComplain_text(feedBackText.getText());
    	complaint.setComplain_time(Timestamp.valueOf(LocalDateTime.now()));
    	ClientApp.ProtocolHandler.Invoke(RequestType.MakeComplaint,complaint,null,true);
    	if(((String)ClientApp.ProtocolHandler.GetResponse(RequestType.MakeComplaint)).equals("done"))
    	{
    		ClientApp.ProtocolHandler.Invoke(RequestType.GetComplaints,  null, null, true);
        	LoginController.windowControl.putPipe("Complaints", ClientApp.ProtocolHandler.GetResponse(RequestType.GetComplaints));
        	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    	}
    	else 
    	{
    		errorLabel.setVisible(true);
    		errorLabel.setText((String)ClientApp.ProtocolHandler.GetResponse(RequestType.MakeComplaint));
    	}
    	
    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		errorLabel.setVisible(false);
		ClientApp.ProtocolHandler.Invoke(RequestType.GetBranches,  null, null, true);
		branchField.getItems().addAll((ObservableList<String>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetBranches));
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
