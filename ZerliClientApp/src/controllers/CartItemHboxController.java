package controllers;



import Entities.*;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartItemHboxController extends HBox{
	private Label totalCost;
	private VBox Vbox;
    @FXML
    private Label catalogTypeLabel;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemTypeLabel;

    @FXML
    private Label quntityLabel;
    
    @FXML
    private ImageView image;
    
    @FXML
    private HBox hboxRoot;
    
    private int id;
	private int price;
	private int quntity;
    public CartItemHboxController() {
	super();	
    }
    public void init (Label totalCost, VBox Vbox ,Image image1 ,int id,String name,ItemType itemType,CatalogType catalogType,int quntity,int price)
    {
    	
    	this.totalCost=totalCost;
    	this.Vbox=Vbox;
    	this.id=id;
    	this.price=price;
    	this.quntity=quntity;
    	image.setImage(image1);
    	itemNameLabel.setText(name);
    	itemTypeLabel.setText(itemType.toString());
    	catalogTypeLabel.setText(catalogType.toString());
    	quntityLabel.setText(String.valueOf(quntity));
    }
    @FXML
    void DeleteBtnPressed(ActionEvent event) {
    	Vbox.getChildren().remove(hboxRoot);
    	ClientApp.ProtocolHandler.Invoke(RequestType.DeleteItemFromCart, id, null, false);
    	totalCost.setText(String.valueOf((int)LoginController.windowControl.getPipe("totalCost")-(price*quntity)));
    	LoginController.windowControl.putPipe("totalCost", Integer.parseInt(totalCost.getText()));
    }

}