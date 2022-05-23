package controllers;

import Entities.Complaint;
import Entities.Order;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerServiceViewComplaintInfoController implements UserControl{

	private Complaint complaint;
	
    @FXML
    private TextArea AnswerText;

    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Label branchLabel;

    @FXML
    private Label complaintText;

    @FXML
    private Label dateLabel;


    @FXML
    private TextField refundField;

    @FXML
    private Label userIDlLabel;
    
    @FXML
    private Label errorLabel;

    @FXML
    void EndPressed(ActionEvent event) {
    	try {
    		int refund = Integer.parseInt(refundField.getText());
    		if(refund < 0)
    		{
    			errorLabel.setVisible(true);
        		errorLabel.setText("refund must be positive");
    		}
    		else
    		{
    			complaint.setAnswer_text(AnswerText.getText());
    			complaint.setRefund(refund);
    			ClientApp.ProtocolHandler.Invoke(RequestType.ComplaintResponse,complaint , null, false);
    			ClientApp.ProtocolHandler.Invoke(RequestType.GetComplaints,  null, null, true);
    			LoginController.windowControl.putPipe("Complaints", ClientApp.ProtocolHandler.GetResponse(RequestType.GetComplaints));
        		LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    		}
    	}catch (NumberFormatException e)
    	{
    		errorLabel.setVisible(true);
    		errorLabel.setText("Please enter a Number");
    	}
    		
    
    }

    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerServiceViewComplaints.fxml");
    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		complaint = (Complaint)LoginController.windowControl.peekPipe("Complaint select");
		complaintText.setText(complaint.getComplain_text());
		userIDlLabel.setText(complaint.getUser_id());
		dateLabel.setText(complaint.getComplain_time().toString());
		branchLabel.setText(complaint.getBranch());
		
		
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
