package controllers;

import java.io.IOException;
import java.util.ArrayList;

import Entities.ItemInList;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerCartController implements UserControl {
	private ObservableList<ItemInList> itemList;
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
    	ClientApp.ProtocolHandler.Invoke(RequestType.GetCart, null, null, true);
		itemList=(ObservableList<ItemInList>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetCart);
    	if(itemList.size() == 0) return;
    	else {
    		clearScreen();
        	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerOrderInformation.fxml");
    	}
    	
    }
	@Override
	public void onEnter() {
		ClientApp.ProtocolHandler.Invoke(RequestType.GetCart, null, null, true);
		itemList=(ObservableList<ItemInList>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetCart);
		clearScreen();
		for (ItemInList itemInlist : itemList) {
			totalCost+=itemInlist.getPrice()*itemInlist.getQuantity();
			FXMLLoader loader=new FXMLLoader();
			HBox root;
			loader.setLocation(getClass().getResource("/gui/components/CartItemHbox.fxml"));
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			CartItemHboxController cartItemHboxController=loader.getController();
			cartItemHboxController.init(totalCostLabel,Vbox,itemInlist.getJXImage(),itemInlist.getItem_id(),
					itemInlist.getItemName(),itemInlist.getItem_type(),itemInlist.getCatalog_Type(),
					itemInlist.getQuantity(),itemInlist.getPrice());
			Vbox.getChildren().add(root);
		}
		ArrayList<ItemInList> items = new ArrayList<ItemInList>();
		for(ItemInList item : itemList)
			items.add(item);
		totalCostLabel.setText(String.valueOf(totalCost));
		LoginController.windowControl.putPipe("totalCost", totalCost);
		LoginController.windowControl.putPipe("CartItems", items);
	}
	private void clearScreen() {
		Vbox.getChildren().clear();
		totalCost=0;
	}
	@Override
	public void onExit() {
		clearScreen();
	}
}

