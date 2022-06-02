package controllers;

import Entities.*;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

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
    private Rectangle imageRec;
	
    @FXML
    private VBox V;

    @FXML
    private Button addToCartBtn;

    @FXML
    private ImageView plusBtn;

    @FXML
    private Label quntityLabel;
    
    @FXML
    private Label addLbl;
    
    public ItemHBoxController() {
    	super();
	}
    
    @FXML
    void AddToCart(ActionEvent event) {
    	if(ClientApp.UserStatus == Access.inactive)  {
    		Alert confirmAlert = new Alert(AlertType.NONE);
			confirmAlert.setTitle("Not registered customer");
			confirmAlert.setContentText("Your account is inactive\nContact Zerli's administration");
			ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
			confirmAlert.getDialogPane().getButtonTypes().add(ok);
			confirmAlert.showAndWait();
			return;
    	}
    	
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
    	
    	Thread thread =new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
		        	addToCartBtn.setVisible(false);
		        	addLbl.setVisible(true);
		        	Thread.sleep(1000);
		        	addLbl.setVisible(false);
		        	addToCartBtn.setVisible(true);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();

    }
    
    public void init(int id,String name,String costLabel,Image image,boolean onSale) 
    {
    	if(LoginController.windowControl.peekPipe("catalog") == CatalogType.new_item) {
    		addToCartBtn.setText("Add to"+"\n"+"private product");
    	}
    	this.id=id;
    	addLbl.setVisible(false);

    	CostLabel.setText(costLabel+ " " + Utilities.Constants.SHEKEL);
		nameLabel.setText(name);
		imageRec.setFill(new ImagePattern(image));
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

