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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ItemHBoxController extends HBox {
	private int counter=0;
	private int id;
    @FXML
    private Label saleLabel2;
    
    @FXML
    private Label nameLabel;
    
	@FXML
    private Label CostLabel;
	
	@FXML
	private ImageView itemImage;
	
    @FXML
    private VBox V;

    @FXML
    private Button addToCartBtn;

    @FXML
    private ImageView plusBtn;

    @FXML
    private Label quntityLabel;
    
    public ItemHBoxController() {
    	super();
	}
    
    @FXML
    void AddToCart(ActionEvent event) {
    	if(Integer.parseInt(quntityLabel.getText()) == 0) return;
    	if(LoginController.windowControl.peekPipe("catalog") == CatalogType.new_item) 
    	{
    		NewItem myItem = (NewItem) LoginController.windowControl.peekPipe("newItem");
    		
    		if( myItem == null){
    			myItem = new NewItem();
    		}
    		
    		ItemInList item = new ItemInList(id, nameLabel.getText(),Integer.parseInt(quntityLabel.getText()));
    		item.setPrice(Integer.parseInt(CostLabel.getText().substring(0,CostLabel.getText().indexOf(' '))));
			myItem.addItem(item);
			LoginController.windowControl.putPipe("newItem", myItem);
    	}
    	else {
    		RedNotificationCircle cartLabelAndImage=(RedNotificationCircle) LoginController.windowControl.getPipe("cartLabel");
        	int cartNotificationsNumber=cartLabelAndImage.getCartNotificationsNumber();
        	ItemInList newItem = new ItemInList(id, Integer.parseInt(quntityLabel.getText()));
        	ClientApp.ProtocolHandler.Invoke(RequestType.AddToCart, newItem, null, true);
        	cartNotificationsNumber=cartNotificationsNumber+(int)ClientApp.ProtocolHandler.GetResponse(RequestType.AddToCart);
        	cartLabelAndImage.setCartNotificationsNumber(cartNotificationsNumber);
        	
          	Label label;
        	ImageView image=new ImageView();
        	label=cartLabelAndImage.getLabel();
        	image=cartLabelAndImage.getImage();
        	label.setVisible(true);
        	image.setVisible(true);
        	label.setText(String.valueOf(cartNotificationsNumber));
        	LoginController.windowControl.putPipe("cartLabel", cartLabelAndImage);
    		
    	}
        	
    	counter = 0;
    	quntityLabel.setText("0");

    }
    
    public void init(int id,String name,String costLabel,Image image,boolean onSale) 
    {
    	if(LoginController.windowControl.peekPipe("catalog") == CatalogType.new_item) {
    		addToCartBtn.setText("Add to"+"\n"+"private product");
    	}
    	this.id=id;
    	CostLabel.setText(costLabel+ " " + Utilities.Constants.SHEKEL);
		nameLabel.setText(name);
		itemImage.setImage(image);
		quntityLabel.setText("0");
		saleLabel2.setVisible(onSale);
    }

    @FXML
    void MinusBtnPressed(MouseEvent event) {
    	if(counter>0)
    	{
    		counter--;
    		quntityLabel.setText(String.valueOf(counter));
    	}
    }

    @FXML
    void PlusBtnPressed(MouseEvent event) {
     	counter++;
    	quntityLabel.setText(String.valueOf(counter));
    }

}

