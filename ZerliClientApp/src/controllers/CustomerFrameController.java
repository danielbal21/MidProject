package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Entities.Access;
import Entities.ItemInList;
import Entities.NotificationInTable;
import Entities.RedNotificationCircle;
import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomerFrameController implements IContainable{
	
	Map<String,Parent> map = new HashMap<>();
	Map<String,UserControl> uc_map = new HashMap<String, UserControl>();
	Thread thread;
	
    @FXML
    private ImageView bellBtn;

    @FXML
    private ImageView cartBtn;

    @FXML
    private AnchorPane controlContainer;

    @FXML
    private ImageView exitBtn;

    @FXML
    private ImageView homeBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private Button ordersBtn;
    
    @FXML
    private ImageView cartRedCircleImage;
    @FXML
    private Label cartRedCricleLable;
    
    @FXML
    private ImageView bellRedCircleImage;
    @FXML
    private Label bellRedCricleLable;
    
    private int cartNotificationsNumber;
    
    void init(){
    	ClientApp.ProtocolHandler.Invoke(RequestType.GetCart, null, null, true);
    	ObservableList<ItemInList> list=(ObservableList<ItemInList>)ClientApp.ProtocolHandler.GetResponse(RequestType.GetCart);
    	for (ItemInList itemInList : list) {
    		cartNotificationsNumber+=itemInList.getQuantity();
		}
    	if (cartNotificationsNumber>0)
    	{
    		cartRedCircleImage.setVisible(true);
    		cartRedCricleLable.setText(String.valueOf(cartNotificationsNumber));
    		cartRedCricleLable.setVisible(true);
    	}
    	nameLabel.setText(ClientApp.UserID.toString());
    	RedNotificationCircle cartNotification=new RedNotificationCircle(cartRedCircleImage, cartRedCricleLable, cartNotificationsNumber);
    	LoginController.windowControl.putPipe("cartLabel", cartNotification);
    
    	bellRedCricleLable.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(Integer.parseInt(newValue) <= 0)
				{
					bellRedCircleImage.setVisible(false);
					bellRedCricleLable.setVisible(false);
				}
				else
				{
					bellRedCircleImage.setVisible(true);
					bellRedCricleLable.setVisible(true);
				}
			}
		});
    	
    	
    
     	   	
		thread =new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					ClientApp.ProtocolHandler.Invoke(RequestType.GetNotification, null, null, true);
					ObservableList<NotificationInTable> list = (ObservableList<NotificationInTable>) ClientApp.ProtocolHandler.GetResponse(RequestType.GetNotification);
					LoginController.windowControl.putPipe("All Notification", list);
					int cnt=0;
					for(NotificationInTable not: list) {
						if(not.getStatus().equals("unread")) cnt++;
					}
					
					String notificationNumber=String.valueOf(cnt);
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							bellRedCricleLable.setText(notificationNumber);							
						}
			    		});
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						
					}
				}
			}
		});
		thread.start();
    	
    }

    @FXML
    void bellPressed(MouseEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/NotificationWindow.fxml");
    }

    @FXML
    void cartPressed(MouseEvent event) {
    	if(ClientApp.UserStatus == Access.inactive)  {
    		Alert confirmAlert = new Alert(AlertType.ERROR);
			confirmAlert.setTitle("Not registered customer");
			confirmAlert.setContentText("Your account is inactive\nContact Zerli's administration");
			ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
			confirmAlert.getDialogPane().getButtonTypes().add(ok);
			confirmAlert.showAndWait();
			return;
    	}
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCart.fxml");
    }

    @FXML
    void exitPressed(MouseEvent event) {
    	thread.interrupt();
    	ClientApp.ProtocolHandler.Invoke(RequestType.SetLogOut,null,null,false);
    	try {
			ClientApp.ClientConnection.closeConnection();
			System.out.println(ClientApp.ClientConnection.isConnected());
			System.exit(0);
		} catch (IOException e) {e.printStackTrace();}
    }

    @FXML
    void homePressed(MouseEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerHomePage.fxml");
    }

    @FXML
    void ordersPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerViewOrders.fxml");
    }

    @FXML
    void pressLogout(ActionEvent event) {
    	thread.interrupt();
    	LoginController.windowControl.stage.close();
    	Stage newStage = new Stage();
		Parent root = null;
		try 
		{
			ClientApp.ProtocolHandler.Invoke(RequestType.SetLogOut,null,null,false);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/mainframes/Login.fxml"));
			root =  loader.load(); 
			Utilities.GenericUtilties.SetWindowMovable(root, newStage);
			Scene scene = new Scene(root);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.setScene(scene); 
			newStage.show();
			LoginController.windowControl.stage = newStage;	
		}
		catch (Exception e) {e.printStackTrace();	}
	
    }
    
    @FXML
    void myAccountPressed(ActionEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerAccountInfo.fxml");
    }

	@Override
	public AnchorPane getControlContainer() {
		return controlContainer;		
	}

	


}
