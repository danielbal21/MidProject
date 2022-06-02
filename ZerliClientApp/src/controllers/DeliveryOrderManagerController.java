package controllers;

import Entities.Order;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DeliveryOrderManagerController implements UserControl{

	private ObservableList<Order> observableList;
	
	    @FXML
	    private TableColumn<Order, String> adressCol;
	
	    @FXML
	    private Label branchName;
	
	    @FXML
	    private TableColumn<Order, String> cityCol;

	    @FXML
	    private TableColumn<Order, String> expectedDateColumn;

	    @FXML
	    private TableColumn<Order, String> orderDateColumn;

	    @FXML
	    private TableColumn<Order, String> orderIDcolumn;

	    @FXML
	    private TableView<Order> ordersTable;


	    @FXML
	    private TableColumn<Order, Integer> priceColumn;

	    @FXML
	    void getOrderInfo(MouseEvent event) {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Order OrderChoose = ordersTable.getSelectionModel().getSelectedItem();
				LoginController.windowControl.putPipe("Order select", OrderChoose );
				LoginController.windowControl.setUserControl("/gui/usercontrols/DeliveryViewOrderDetails.fxml");
			}
	    }
	    
	    @FXML
	    void refreshPressed(MouseEvent event) {
	    	onEnter();
	    }
		
		@Override
		public void onEnter() {
			branchName.setText((String) LoginController.windowControl.peekPipe("Branch"));
			orderIDcolumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
			orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("order_date"));
			expectedDateColumn.setCellValueFactory(new PropertyValueFactory<>("shipping_date"));
			adressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
			cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
			
			ClientApp.ProtocolHandler.Invoke(RequestType.GetOrdersByBranch,  LoginController.windowControl.peekPipe("Branch"), "delivery", true);
			observableList = (ObservableList<Order>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetOrdersByBranch);
			ordersTable.setItems(observableList);
		}

		@Override
		public void onExit() {}

}
