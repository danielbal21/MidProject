package controllers;

import Entities.CatalogType;
import Entities.Color;
import Entities.Item;
import Entities.ItemInList;
import Entities.ItemType;
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
import javafx.scene.layout.AnchorPane;

public class CustomerServiceViewComplaintInfoController implements UserControl{

	private Order order;
	
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
    void approvePressed(ActionEvent event) {
    	ClientApp.ProtocolHandler.Invoke(RequestType.ConfirmOrder,  LoginController.windowControl.peekPipe("Order select"), null, false);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerOrderManager.fxml");
    }

    @FXML
    void backPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/ManagerOrderManager.fxml");
    }

	@Override
	public void onEnter() {
		order = (Order)LoginController.windowControl.peekPipe("Order select");
		catalogColumn.setCellValueFactory(new PropertyValueFactory<>("catalog_type"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("item_id"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("item_type"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		orderDateLabel.setText(order.getOrder_date());
		shippingDateLabel.setText(order.getShipping_date());
		paymentMethodLabel.setText(order.getPayment_method());
		orderNumberLabel.setText(order.getOrderID());
		costLabel.setText(String.valueOf(order.getTotalPrice()));
		statusLabel.setText(order.getOrder_status());
		shippingMethodLabel.setText(order.getShipping_method());
		
		ClientApp.ProtocolHandler.Invoke(RequestType.GetItemsOfOrder,  LoginController.windowControl.peekPipe("Order select"), null, true);
		
		observableList = (ObservableList<ItemInList>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetItemsOfOrder);
		
		itemTable.setItems(observableList);
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
