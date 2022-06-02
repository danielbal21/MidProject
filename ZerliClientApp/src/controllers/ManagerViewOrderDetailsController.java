package controllers;

import Entities.CatalogType;
import Entities.ItemInList;
import Entities.ItemType;
import Entities.NewItem;
import Entities.NotificationInTable;
import Entities.Order;
import Entities.OrderStatus;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManagerViewOrderDetailsController implements UserControl{

	private Order order;
	private String futureStatus;
	private ObservableList<ItemInList> orderItems;
	
	private ObservableList<ItemInList> observableList;
	
    @FXML
    private AnchorPane activePanelContainer;


    @FXML
    private TableView<ItemInList> itemTable;
    
    @FXML
    private TableColumn<ItemInList, CatalogType> catalogColumn;


    @FXML
    private Label costLabel;

    @FXML
    private TableColumn<ItemInList, Integer> itemIDColumn;

    @FXML
    private TableColumn<ItemInList, String> itemNameColumn;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private TableColumn<ItemInList, Integer> priceColumn;

    @FXML
    private TableColumn<ItemInList, Integer> quantityColumn;

    @FXML
    private Label shippingDateLabel;

    @FXML
    private Label shippingMethodLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private TableColumn<ItemInList, ItemType> typeColumn;
    

    @FXML
    private Button fullListBtn;
    
    @FXML
    private Label itemListLbl;
	
   @FXML
    private Button approveBtn;
    
    
    @FXML
    void newItemClick(MouseEvent event) {
    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
    		if(itemTable.getSelectionModel().getSelectedItem() instanceof NewItem) {
    			
    			NewItem chosenItem = (NewItem) itemTable.getSelectionModel().getSelectedItem();
    			observableList = FXCollections.observableArrayList(chosenItem.getAssemble());
        		
    			itemTable.setItems(observableList);
        		fullListBtn.setVisible(true);
        		itemListLbl.setText("New Item\nList:");
    		}	
    		else return;
    	}
    	else return;
    }
    

    @FXML
    void showFullSpecs(ActionEvent event) {
    	fullListBtn.setVisible(false);
		itemListLbl.setText("Items\nList:");
		
    	observableList = orderItems;
    	itemTable.setItems(observableList);
    }
    

    @FXML
    void approvePressed(ActionEvent event) {
    	Order order = (Order) LoginController.windowControl.peekPipe("Order select");
    	
    	ClientApp.ProtocolHandler.Invoke(RequestType.ConfirmOrder, order, futureStatus, true);
    	int refund = (int) ClientApp.ProtocolHandler.GetResponse(RequestType.ConfirmOrder);
    	NotificationInTable notification = new NotificationInTable();
    	notification.setTo(order.getUserID());
    	notification.setFrom("Manager");
    	
    	if(futureStatus.equals("confirmed")) {
    		notification.setContent("Order number " + order.getOrderID() + " has been confirmed"
    				+ "\nSee full info in \"My Orders\" tab");
    	}
    	else {
    		notification.setContent("Order number " + order.getOrderID() + " has been canceled"
    				+ "\nPlease receive " + refund + " Zerli coins as refund");
    	}
    	
    	ClientApp.ProtocolHandler.Invoke(RequestType.SendNotification, notification, null, false);
    	
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerOrderManager.fxml");
    }

    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerOrderManager.fxml");
    }

	@Override
	public void onEnter() {
		fullListBtn.setVisible(false);
		itemListLbl.setText("Items\nList:");
		
		order = (Order)LoginController.windowControl.peekPipe("Order select");
		catalogColumn.setCellValueFactory(new PropertyValueFactory<>("catalog_Type"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("item_id"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("item_type"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		orderDateLabel.setText(order.getOrder_date());
		shippingDateLabel.setText(order.getShipping_date());
		paymentMethodLabel.setText(order.getPayment_method());
		orderNumberLabel.setText(order.getOrderID());
		costLabel.setText(String.valueOf(order.getTotalPrice())+ " " + Utilities.Constants.SHEKEL);
		statusLabel.setText(order.getOrder_status());
		shippingMethodLabel.setText(order.getShipping_method());
		
		if(order.getStatus() == OrderStatus.pending_cancel ) {
			futureStatus = "canceled";
			approveBtn.setText("Cancel order");
		}
		else if(order.getStatus() == OrderStatus.pending_confirm ) {
			futureStatus = "confirmed";
			approveBtn.setText("Approve order");

		}
		
		ClientApp.ProtocolHandler.Invoke(RequestType.GetItemsOfOrder, LoginController.windowControl.peekPipe("Order select"), null, true);
		orderItems = (ObservableList<ItemInList>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetItemsOfOrder);
		observableList = orderItems;
		itemTable.setItems(observableList);
		
	}

	@Override
	public void onExit() {
	}

}
