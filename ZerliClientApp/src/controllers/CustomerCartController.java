package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import Entities.Access;
import Entities.ItemInList;
import Entities.NewItem;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerCartController implements UserControl {
	private ObservableList<ItemInList> itemList;
	private int totalCost=0;
    @FXML
    private VBox Vbox;
    
    @FXML
    private Label TotalCostText;

    @FXML
    private Label CartEmpyLabel;
    
    @FXML
    private Label currency;
    
    @FXML
    private Label totalCostLabel;
    
    @FXML
    private AnchorPane activePanelContainer;

    @FXML
    private Button nextBtn;
    @FXML
    void NextBtnPressed(ActionEvent event) {
    	if(ClientApp.UserStatus == Access.frozen) {
    		Alert confirmAlert = new Alert(AlertType.NONE);
    		confirmAlert.setTitle("Acount information");
    		confirmAlert.setContentText("Your account is frozen\nContact Zerli's administration");
    		ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
    		confirmAlert.getDialogPane().getButtonTypes().add(ok);
    		Optional<ButtonType> result = confirmAlert.showAndWait();
    	}
    	else if(!totalCostLabel.getText().equals("0")){
    		clearScreen();
        	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerOrderInformation.fxml");
    	}
 
    }
	@Override
	public void onEnter() {
		totalCostLabel.setText("0");
		currency.setText(Utilities.Constants.SHEKEL);
		CartEmpyLabel.setVisible(false);
		totalCostLabel.setVisible(true);
		currency.setVisible(true);
		TotalCostText.setVisible(true);
		ClientApp.ProtocolHandler.Invoke(RequestType.GetCart, null, null, true);
		itemList=(ObservableList<ItemInList>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetCart);
		clearScreen();
		if(itemList.size()==0)
		{
			clearScreen();
			CartEmpyLabel.setVisible(true);
			totalCostLabel.setVisible(false);
			currency.setVisible(false);
			TotalCostText.setVisible(false);
			return;
		}
		for (ItemInList itemInlist : itemList) {
			FXMLLoader loader=new FXMLLoader();
			HBox root;
			
			if(itemInlist instanceof NewItem)
			{	
				NewItem newItem= (NewItem)itemInlist;
				totalCost+=newItem.getPrice()*newItem.getQuantity();
				loader.setLocation(getClass().getResource("/gui/components/CartNewItemHbox.fxml"));
				try {
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
				CartNewItemController cartNewItemController =loader.getController();
				cartNewItemController.init(totalCostLabel,Vbox,newItem.getJXImage(),
						newItem.getItem_id(),newItem.getItemName(),newItem.getQuantity(),
						newItem.getPrice(),newItem);
			}
			else {
				totalCost+=itemInlist.getPrice()*itemInlist.getQuantity();
				loader.setLocation(getClass().getResource("/gui/components/CartItemHbox.fxml"));
				try {
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
				CartItemHboxController cartItemHboxController=loader.getController();
				cartItemHboxController.init(totalCostLabel,Vbox,itemInlist.getJXImage(),itemInlist.getItem_id(),
						itemInlist.getItemName(),itemInlist.getItemType(),itemInlist.getCatalogType(),itemInlist.getQuantity(),itemInlist.getPrice());
			}
			
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

