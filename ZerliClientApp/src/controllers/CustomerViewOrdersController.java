package controllers;

import java.security.Timestamp;
import Entities.Order;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CustomerViewOrdersController implements UserControl{
	ObservableList<Order> orderList;
	
    @FXML
    private TableView<Order> OrderViewTable;
    
    @FXML
    private TableColumn<Order, String> OrderDateCol;

    @FXML
    private TableColumn<Order, Integer> OrderNumberCol;

    @FXML
    private TableColumn<Order, Integer> priceCol;

    @FXML
    private TableColumn<Order, String> shippingDateCol;

    @FXML
    private TableColumn<Order, String> statusCol;

    @FXML
    void backPressed(ActionEvent event) {
		LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerHomePage.fxml");
    }
    
    @FXML
    void clickOnItem(MouseEvent event) {
    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
    		Order chosenOrder = orderList.get(OrderViewTable.getSelectionModel().getSelectedIndex());
    		LoginController.windowControl.putPipe("chosenOrder", chosenOrder);
    		LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerViewOrdersSpecific.fxml");
    	}
    	else return;
    }

	@Override
	public void onEnter() {
		ClientApp.ProtocolHandler.Invoke(RequestType.GetAllCustomerOrders, null, null, true);
		orderList = (ObservableList<Order>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetAllCustomerOrders);
		
		OrderNumberCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));	
		priceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));	
	    OrderDateCol.setCellValueFactory(new PropertyValueFactory<>("order_date"));
	    shippingDateCol.setCellValueFactory(new PropertyValueFactory<>("shipping_date"));
	    statusCol.setCellValueFactory(new PropertyValueFactory<>("order_status"));
	    OrderViewTable.setItems(orderList);
	}

	@Override
	public void onExit() {
		//orderList.clear();
	}

}
