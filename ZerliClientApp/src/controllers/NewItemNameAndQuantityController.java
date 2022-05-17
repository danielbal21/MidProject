package controllers;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import Entities.CatalogType;
import Entities.NewItem;
import Entities.RedNotificationCircle;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class NewItemNameAndQuantityController implements UserControl{
	private NewItem newitem;
	
    @FXML
    private Label NameErrorLabel;

    @FXML
    private TextField NameText;

    @FXML
    private Label QuantityErrorLabel;

    @FXML
    private Label QuantityNumberText;

    @FXML
    private AnchorPane activePanelContainer;
    
    @Override
	public void onEnter() {
    	ResetBtn(null);
	}
    
    @FXML
    void AddQuantity(MouseEvent event) { 
    	QuantityNumberText.setText(toString().valueOf((Integer.parseInt(QuantityNumberText.getText())+1)));
    }

    @FXML
    void BackBtnPressed(ActionEvent event) {
    	LoginController.windowControl.putPipe("NewItem", CatalogType.new_item);
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCatalogViewer.fxml");
    }

    @FXML
    void NextBtnPressed(MouseEvent event) {
    	
    	if(NameText.getText().equals(""))
    	{
    		NameErrorLabel.setVisible(true);
    		
			Thread thread =new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					NameErrorLabel.setVisible(false);
				}
			});
			thread.start();
    		return;
    	}
    	
    	if(Integer.parseInt(QuantityNumberText.getText())<1)
    	{
    		QuantityErrorLabel.setVisible(true);
    		
			Thread thread =new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					QuantityErrorLabel.setVisible(false);
				}
			});
			thread.start();
    		return;
    	}
    	newitem=(NewItem)LoginController.windowControl.getPipe("newItem");
    	newitem.setItemName(NameText.getText());
    	newitem.setQuantity(Integer.parseInt(QuantityNumberText.getText()));
    	ClientApp.ProtocolHandler.Invoke(RequestType.UpdateNewItemInCart, newitem, null, false);
    
    	RedNotificationCircle cartLabelAndImage=(RedNotificationCircle) LoginController.windowControl.getPipe("cartLabel");
    	int cartNotificationsNumber=cartLabelAndImage.getCartNotificationsNumber();
    	cartNotificationsNumber=cartNotificationsNumber+Integer.parseInt(QuantityNumberText.getText());
    	cartLabelAndImage.setCartNotificationsNumber(cartNotificationsNumber);
    	
    	Label label;
    	ImageView image=new ImageView();
    	label=cartLabelAndImage.getLabel();
    	image=cartLabelAndImage.getImage();
        label.setVisible(true);
        image.setVisible(true);
    	label.setText(String.valueOf(cartNotificationsNumber));
    	LoginController.windowControl.putPipe("cartLabel", cartLabelAndImage);
    	
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerHomePage.fxml");
    }

    @FXML
    void RemoveQuantity(MouseEvent event) {
    	if(Integer.parseInt(QuantityNumberText.getText())<1)
    		return;
    	QuantityNumberText.setText(toString().valueOf((Integer.parseInt(QuantityNumberText.getText())-1)));
    }
    
	@Override
	public void onExit() {
		
	}


    @FXML
    void ResetBtn(MouseEvent event) {
       	NameText.setText("");
    	QuantityNumberText.setText("0");
    }
}
