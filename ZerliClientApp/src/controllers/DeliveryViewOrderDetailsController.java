package controllers;

import Entities.Order;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;



public class DeliveryViewOrderDetailsController implements UserControl {

	 @FXML
	    private AnchorPane activePanelContainer;

	    @FXML
	    private Label addressLbl;

	    @FXML
	    private Label cityLbl;

	    @FXML
	    private Label costLabel;

	    @FXML
	    private Label fullnameLbl;

	    @FXML
	    private Label orderDateLabel;

	    @FXML
	    private Label orderNumberLabel;

	    @FXML
	    private Label paymentMethodLabel;

	    @FXML
	    private Label phoneLbl;

	    @FXML
	    private Label shippingDateLabel;


    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/DeliveryOrderManager.fxml");
    }
 
    @FXML
    void donePressed(ActionEvent event) {
    	ClientApp.ProtocolHandler.Invoke(RequestType.EndOrder,  LoginController.windowControl.peekPipe("Order select"), "done", false);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/DeliveryOrderManager.fxml");
    }

	@Override
	public void onEnter() {
		Order order = (Order)LoginController.windowControl.peekPipe("Order select");
		orderDateLabel.setText(order.getOrder_date());
		shippingDateLabel.setText(order.getShipping_date());
		paymentMethodLabel.setText(order.getPayment_method());
		orderNumberLabel.setText(order.getOrderID());
		costLabel.setText(String.valueOf(order.getTotalPrice()));
		addressLbl.setText(order.getAddress());
		cityLbl.setText(order.getCity());
		fullnameLbl.setText(order.getFullname());
		phoneLbl.setText(order.getPhone());
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
