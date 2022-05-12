package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ProtocolHandler.RequestType;
import client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomerFrameController implements IContainable{
	
	Map<String,Parent> map = new HashMap<>();
	Map<String,UserControl> uc_map = new HashMap<String, UserControl>();

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
    private ImageView bellRedCircleImage;
    @FXML
    private Label cartRedCricleLable;
    @FXML
    private Label bellRedCricleLable;
    
    void init(){
    	nameLabel.setText(ClientApp.UserID.toString());
    }

    @FXML
    void bellPressed(MouseEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/NotificationWindow.fxml");
    }

    @FXML
    void cartPressed(MouseEvent event) {
    	LoginController.windowControl.setUserControl("/gui/usercontrols/CustomerCart.fxml");
    }

    @FXML
    void exitPressed(MouseEvent event) {
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

	@Override
	public AnchorPane getControlContainer() {
		return controlContainer;		
	}

	


}