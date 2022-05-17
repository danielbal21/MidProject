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
    private Button cancelOrder;
    

    @FXML
    private Label costLabel;

    @FXML
    private Button done;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label shippingDateLabel;

    @FXML
    private Label shippingMethodLabel;

    @FXML
    private Label statusLabel;

    @FXML
    void backPressed(ActionEvent event) {

    }

    @FXML
    void cancelPressed(ActionEvent event) {
    	ClientApp.ProtocolHandler.Invoke(RequestType.EndOrder,  LoginController.windowControl.peekPipe("Order select"), "cancel", false);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/DeliveryOrderManager.fxml");
    }

    @FXML
    void donePressed(ActionEvent event) {
    	ClientApp.ProtocolHandler.Invoke(RequestType.EndOrder,  LoginController.windowControl.peekPipe("Order select"), "done", false);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/DeliveryOrderManager.fxml");
    }

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		Order order = (Order)LoginController.windowControl.peekPipe("Order select");
		orderDateLabel.setText(order.getOrder_date());
		shippingDateLabel.setText(order.getShipping_date());
		paymentMethodLabel.setText(order.getPayment_method());
		orderNumberLabel.setText(order.getOrderID());
		costLabel.setText(String.valueOf(order.getTotalPrice()));
		statusLabel.setText(order.getOrder_status());
		shippingMethodLabel.setText(order.getShipping_method());
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
