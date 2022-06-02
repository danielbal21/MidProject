package controllers;

import Entities.Order;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ManagerOrderManagerController implements UserControl {

	private ObservableList<Order> observableList;
	
	   @FXML
	    private Label branchName;

	    @FXML
	    private TableColumn<Order, String> expectedDateColumn;

	    @FXML
	    private TableColumn<Order, String> orderDateColumn;

	    @FXML
	    private TableColumn<Order, String> orderIDcolumn;

	    @FXML
	    private TableView<Order> ordersTable;

	    @FXML
	    private TableColumn<Order, String> shippingMethodColumn;

	    @FXML
	    private TableColumn<Order, String> statusColumn;


	    @FXML
	    void backPressed(ActionEvent event) 
	    {
	    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerHomePage.fxml");
	    }

	    @FXML
	    void getOrderInfo(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			Order OrderChoose = ordersTable.getSelectionModel().getSelectedItem();
			LoginController.windowControl.putPipe("Order select", OrderChoose );
			LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerViewOrderDetails.fxml");
		}
	}
	    

    @FXML
    void refreshPressed(MouseEvent event) {
    	onEnter();
    }

	@Override
	public void onEnter() {
		branchName.setText((String) LoginController.windowControl.peekPipe("Branch"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("order_date"));
		orderIDcolumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		expectedDateColumn.setCellValueFactory(new PropertyValueFactory<>("shipping_date"));
		shippingMethodColumn.setCellValueFactory(new PropertyValueFactory<>("shipping_method"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("Order_status"));
		
		ClientApp.ProtocolHandler.Invoke(RequestType.GetOrdersByBranch,  LoginController.windowControl.peekPipe("Branch"), "manager", true);
		observableList = (ObservableList<Order>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetOrdersByBranch);
		
		ordersTable.setItems(observableList);
	}

	@Override
	public void onExit() {
	}



}
