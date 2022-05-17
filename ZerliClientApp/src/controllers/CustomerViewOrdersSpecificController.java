package controllers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import Entities.CatalogType;
import Entities.ItemInList;
import Entities.ItemType;
import Entities.Order;
import Entities.OrderStatus;
import Entities.ShippingMethods;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerViewOrdersSpecificController implements UserControl{
	
	ObservableList<ItemInList> itemListView =
			FXCollections.observableArrayList(new ArrayList<ItemInList>());
	    
	private Order order;
	
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
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerViewOrders.fxml");

    }
    
    @FXML
    void cancelOrderPressed(ActionEvent event) {
    	Alert confirmAlert = new Alert(AlertType.NONE);
		confirmAlert.setTitle("Cancel Order!");
		confirmAlert.setContentText("Are you sure you want cancel the order?");
		ButtonType yes = new ButtonType("Yes", ButtonData.YES);
		ButtonType no = new ButtonType("No",ButtonData.NO);
		confirmAlert.getDialogPane().getButtonTypes().add(yes);
		confirmAlert.getDialogPane().getButtonTypes().add(no);
		Optional<ButtonType> result = confirmAlert.showAndWait();
		result.ifPresent(response -> { 
			if(response == yes) {
				LocalDateTime cancelTime = LocalDateTime.now();
				LocalDateTime shippingTime = Utilities.GenericUtilties.
						Convert_LocalDate_To_SQLDate(order.getShippingDate());
				
				long diff = Math.abs(ChronoUnit.MINUTES.between(cancelTime,shippingTime));
				int refundZerli=0;
				int	orderPrice = order.getTotalPrice();
				if(diff/60 >= 3 && diff%60 >0) {
					refundZerli=orderPrice;
				}
				else if(diff/60 >= 1 && diff/60 <= 3 && diff%60 >0 ) {
					refundZerli=(int)(0.5*orderPrice);
				}
				else {
					refundZerli=0;
				}
				
				ClientApp.ProtocolHandler.Invoke(RequestType.CancelOrder, refundZerli ,Integer.parseInt(order.getOrderID()), false);
				statusOrder.setText("Pending cancel");
				confirmAlert.close();
				cancelOrder.setVisible(false);
			}
			confirmAlert.close();
		});
    }

	@Override
	public void onEnter() {
		order = (Order) LoginController.windowControl.peekPipe("chosenOrder");
		itemListView = FXCollections.observableArrayList((ArrayList<ItemInList>)order.getItems());
		
		catalogItem.setCellValueFactory(new PropertyValueFactory<>("catalog_Type"));
		nameItem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		priceItem.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityItem.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		typeItem.setCellValueFactory(new PropertyValueFactory<>("item_type"));
		
		ItemTable.setItems(itemListView);
	    
		orderNumber.setText(order.getOrderID());
		orderDate.setText(order.getOrder_date());
		brachName.setText(order.getBranchName());
		if(order.getShippingMethod()== ShippingMethods.shipping) {
			shipMethod.setText("Shipping to");
			fullAddress.setText(order.getAddress()+" "+order.getCity());
		}
		else {
			shipMethod.setText("Pickup at");
			fullAddress.setText(order.getBranchName()+ " branch");
		}
		shippingDate.setText("at " + order.getShipping_date());
		paymentMethod.setText(order.getPayment_method());
		greetingCardText.setText(order.getGreetingCard()==null? "No greeting card" : order.getGreetingCard());
		statusOrder.setText(order.getOrder_status());
		price.setText(String.valueOf(order.getTotalPrice()));
		
		if(order.getStatus() == OrderStatus.completed || 
				order.getStatus() == OrderStatus.canceled ||
				order.getStatus() == OrderStatus.pending_cancel)
			cancelOrder.setVisible(false);
		else 
			cancelOrder.setVisible(true);
		
	}

	@Override
	public void onExit() {}

}
