package controllers;

import java.io.IOException;

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


public class CustomerFrameController {
    @FXML
    private Button backBtn;

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
    private Stage stage;
    @FXML
    void backPressed(ActionEvent event) {

    }

    @FXML
    void bellPressed(MouseEvent event) {

    }

    @FXML
    void cartPressed(MouseEvent event) {
    	//gotocart viewer
    }

    @FXML
    void exitPressed(MouseEvent event) {
    	ClientApp.ProtocolHandler.Invoke(RequestType.SetLogOut,null,null,false);
    	try {
			ClientApp.ClientConnection.closeConnection();
			System.out.println(ClientApp.ClientConnection.isConnected());
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
    }

    @FXML
    void homePressed(MouseEvent event) {
    	setControlContainer("/gui/usercontrols/CustomerHomePage.fxml");
    }

    @FXML
    void ordersPressed(ActionEvent event) {
    	//setOrderContainer();
    }

    @FXML
    void pressLogout(ActionEvent event) {
    	stage.close();
		Stage newStage = new Stage();
		LoginController c;
		Parent root = null;
		try 
		{
			ClientApp.ProtocolHandler.Invoke(RequestType.SetLogOut,null,null,false);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/mainframes/Login.fxml"));
			root =  loader.load(); 
			c = loader.getController();
			Utilities.GenericUtilties.SetWindowMovable(root, newStage);
			Scene scene = new Scene(root);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.setScene(scene); 
			c.setStage(newStage);
			newStage.show();
			
		}
		catch (Exception e) {e.printStackTrace();	}
	
    }


	public FXMLLoader setControlContainer(String string) {
		if(controlContainer.getChildren()!=null)
		{ 
			controlContainer.getChildren().clear();
		}
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource(string));
		try {
			controlContainer.getChildren().add(loader.load());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loader;
		
	}
	public void setStage(Stage stage)
	{
		this.stage=stage;
	}


}
