package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import Entities.*;

public class NewItemListController implements UserControl {

    @FXML
    private Button BackBtn;

    @FXML
    private VBox Vbox;

    @FXML
    private AnchorPane activePanelContainer;
    @FXML
    void BackBtnPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCart.fxml");
    }
	@Override
	public void onEnter() {
		Vbox.getChildren().clear();
		NewItem newItem;
		newItem=(NewItem) LoginController.windowControl.peekPipe("newItemList");
		
		for (ItemInList itemInlist : newItem.getAssemble()) {
			FXMLLoader loader=new FXMLLoader();
			HBox root;
			loader.setLocation(getClass().getResource("/gui/components/CartNewItemListHbox.fxml"));
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			CartNewItemListHboxController cartNewItemListHboxController=loader.getController();
			cartNewItemListHboxController.init(Vbox,itemInlist.getJXImage(),itemInlist.getItem_id(),
					itemInlist.getItemName(),itemInlist.getItemType(),itemInlist.getCatalogType(),itemInlist.getQuantity(),itemInlist.getPrice());
			Vbox.getChildren().add(root);
		}
		
		}
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}
