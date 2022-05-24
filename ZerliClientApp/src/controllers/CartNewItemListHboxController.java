package controllers;

import Entities.*;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CartNewItemListHboxController extends HBox{
	private int id;
	private int price;
	private int quntity;
	private Label totalCost;
	private VBox Vbox;
    @FXML
    private Label catalogTypeLabel;

    @FXML
    private Button deleteBtn;

    @FXML
    private HBox hboxRoot;

    @FXML
    private ImageView image;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemTypeLabel;

    @FXML
    private Label quntityLabel;

    public void init ( VBox Vbox ,Image image1 ,int id,String name,
    		ItemType itemType,CatalogType catalogType,int quntity,int price)
    {
    	this.Vbox=Vbox;
    	this.id=id;
    	this.price=price;
    	this.quntity=quntity;
    	image.setImage(image1);
    	itemNameLabel.setText(name);
    	itemTypeLabel.setText(Utilities.GenericUtilties.ItemTypeToString(itemType));
    	catalogTypeLabel.setText(Utilities.GenericUtilties.CatalogTypeToString(catalogType));
    	quntityLabel.setText(String.valueOf(quntity));
    }
    @FXML
    void DeleteBtnPressed(ActionEvent event) {
    	NewItem newItem =(NewItem) LoginController.windowControl.peekPipe("newItemList");
    	ItemInList tmpInList = new ItemInList(id, quntity);
		newItem.deleteItem(tmpInList);
		LoginController.windowControl.putPipe("newItemList",newItem);
    	Vbox.getChildren().remove(hboxRoot);
    	ClientApp.ProtocolHandler.Invoke(RequestType.DeleteItemFromNewItemList, newItem.getItem_id(), id, true);
    
    	if((boolean)ClientApp.ProtocolHandler.GetResponse(RequestType.DeleteItemFromNewItemList))
    	{
    	

         	if(newItem.getAssemble().size()==0) {
         		RedNotificationCircle cartLabelAndImage=(RedNotificationCircle) LoginController.windowControl.getPipe("cartLabel");
            	int cartNotificationsNumber=cartLabelAndImage.getCartNotificationsNumber();
            	cartNotificationsNumber=cartNotificationsNumber-newItem.getQuantity();
            	cartLabelAndImage.setCartNotificationsNumber(cartNotificationsNumber);
            	Label label;
            	ImageView image=new ImageView();
            	label=cartLabelAndImage.getLabel();
            	image=cartLabelAndImage.getImage();
            	if(cartNotificationsNumber==0) 
            	{
            		label.setVisible(false);
                	image.setVisible(false);
            	}
            	label.setText(String.valueOf(cartNotificationsNumber));
            	LoginController.windowControl.putPipe("cartLabel", cartLabelAndImage);
                ClientApp.ProtocolHandler.Invoke(RequestType.DeleteNewItemFromCart, newItem.getItem_id(), null, false);
                LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCart.fxml");
         	}
    	}
    		
    	
    }

}
