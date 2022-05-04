package controllers;

import Entities.CatalogType;
import Entities.Item;
import Entities.ItemType;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerCartController {
	private ObservableList<Item> itemList;
	private int totalCost=0;
    @FXML
    private VBox Vbox;

    @FXML
    private Label totalCostLabel;
    
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Button nextBtn;

    @FXML
    void NextBtnPressed(ActionEvent event) {

    }
    public CustomerCartController() {
		ClientApp.ProtocolHandler.Invoke(RequestType.GetCart, null, null, true);
		itemList=(ObservableList<Item>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetCart);
		for (Item item : itemList) {
			totalCost+=item.getPrice();
			
			//HBox itemHBoxController=new CartItemHboxController(item.getId(),item.getName(),item.getItemType(),item.getCatalogType(),item.get);
			//Vbox.getChildren().add(itemHBoxController);
		}
		totalCostLabel.setText(String.valueOf(totalCost));
    }
}

