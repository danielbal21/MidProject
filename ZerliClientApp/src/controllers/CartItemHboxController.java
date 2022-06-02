package controllers;



import Entities.*;
import ProtocolHandler.RequestType;
import Utilities.GenericUtilties;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

public class CartItemHboxController extends HBox{
	private Label totalCost;
	private VBox Vbox;
    @FXML
    private Label catalogTypeLabel;
    
    @FXML
    private Circle circle;
    
    @FXML
    private Button deleteBtn;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemTypeLabel;

    @FXML
    private Label quntityLabel;
    
    @FXML
    private HBox hboxRoot;
    
    private int id;
	private int price;
	private int quntity;
    public CartItemHboxController() {
	super();	
    }
    public void init (Label totalCost, VBox Vbox ,Image image1 ,int id,String name,
    		ItemType itemType,CatalogType catalogType,int quntity,int price)
    {
    	this.totalCost=totalCost;
    	this.Vbox=Vbox;
    	this.id=id;
    	this.price=price;
    	this.quntity=quntity;
    	circle.setFill(new ImagePattern(image1));
    	itemNameLabel.setText(name);
    	itemTypeLabel.setText(Utilities.GenericUtilties.ItemTypeToString(itemType));
    	catalogTypeLabel.setText(Utilities.GenericUtilties.CatalogTypeToString(catalogType));
    	quntityLabel.setText(String.valueOf(quntity));
    }
    @FXML
    void DeleteBtnPressed(ActionEvent event) {
    	Vbox.getChildren().remove(hboxRoot);
    	ClientApp.ProtocolHandler.Invoke(RequestType.DeleteItemFromCart, id, null, true);
    	totalCost.setText(String.valueOf((int)LoginController.windowControl.getPipe("totalCost")-(price*quntity)));
    	if((boolean)ClientApp.ProtocolHandler.GetResponse(RequestType.DeleteItemFromCart))
    	{
    		RedNotificationCircle cartLabelAndImage=(RedNotificationCircle) LoginController.windowControl.getPipe("cartLabel");
        	int cartNotificationsNumber=cartLabelAndImage.getCartNotificationsNumber();
        	cartNotificationsNumber=cartNotificationsNumber-quntity;
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
    	}
    	LoginController.windowControl.putPipe("totalCost", Integer.parseInt(totalCost.getText()));
    	
    }

}
