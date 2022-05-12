package controllers;

import java.util.ArrayList;

import Entities.CatalogType;
import Entities.ItemInList;
import Entities.ItemType;
import Entities.Order;
import Entities.OrderStatus;
import Entities.PaymentMethods;
import Entities.ShippingMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerViewOrdersSpecificController implements UserControl{
	
	ObservableList<ItemInList> itemListView =
			FXCollections.observableArrayList(new ArrayList<ItemInList>());
	
    @FXML
    private TableView<ItemInList> ItemTable;
	
    @FXML
    private TableColumn<ItemInList, CatalogType> catalogItem;

    @FXML
    private TableColumn<ItemInList, String> nameItem;
    
    @FXML
    private TableColumn<ItemInList, Integer> priceItem;

    @FXML
    private TableColumn<ItemInList, Integer> quantityItem;
    
    @FXML
    private TableColumn<ItemInList, ItemType> typeItem;
    
	private Order order;
	
    @FXML
    private Label brachName;

    @FXML
    private Button cancelOrder;

    @FXML
    private Label fullAddress;

    @FXML
    private Label greetingCardText;

    @FXML
    private Label orderDate;

    @FXML
    private Label orderNumber;

    @FXML
    private Label paymentMethod;

    @FXML
    private Label price;

    @FXML
    private Label shipMethod;

    @FXML
    private Label shippingDate;

    @FXML
    private Label statusOrder;
    
    @FXML
    private Label linkWord;

    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerViewOrders.fxml");

    }
    
    @FXML
    void cancelOrderPressed(ActionEvent event) {

    }

	@Override
	public void onEnter() {
		order = (Order) LoginController.windowControl.peekPipe("chosenOrder");
		itemListView = FXCollections.observableArrayList((ArrayList<ItemInList>)order.getItems());
		
		catalogItem.setCellValueFactory(new PropertyValueFactory<>("catalog_type"));
		nameItem.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		priceItem.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityItem.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		typeItem.setCellValueFactory(new PropertyValueFactory<>("item_type"));
		
		ItemTable.setItems(itemListView);
	    
		orderNumber.setText(order.getOrderID());
		orderDate.setText(order.getOrder_date());
		brachName.setText(order.getBranchName());
		if(order.getShippingMethod()== ShippingMethods.shipping) {
			shipMethod.setText("Shipping");
			linkWord.setText("to");
			fullAddress.setText(order.getAddress()+" "+order.getCity());
		}
		else {
			shipMethod.setText("Pickup");
			linkWord.setText("at");
			fullAddress.setText(order.getBranchName()+ " branch");
		}
		shippingDate.setText(order.getShipping_date());
		paymentMethod.setText(order.getPayment_method());
		greetingCardText.setText(order.getGreetingCard()==null? "No greeting card" : order.getGreetingCard());
		statusOrder.setText(order.getOrder_status());
		price.setText(String.valueOf(order.getTotalPrice()));
		
		if(order.getStatus() == OrderStatus.complete || order.getStatus() == OrderStatus.cancel) {
			cancelOrder.setVisible(false);
		}
		else {
			cancelOrder.setVisible(true);
		}
	}

	@Override
	public void onExit() {
		if(itemListView != null)
			itemListView.clear();
	}

}
