package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.*;

import java.io.IOException;

public class StartSQLPassswordConfigController {

    @FXML
    private Button connectBtn;

    @FXML
    private Label errrorLabel;

    @FXML
    private ImageView exitBtn;

    @FXML
    private TextField SqlTextField;

    @FXML
    private Label enterSQLLabel;

	private Stage stage;

    @FXML
    void connectPressed(ActionEvent event) {
    	String ip = SqlTextField.getText();
    	if(ip.equals("")) {
    		errrorLabel.setVisible(true);
    	}
    	else {
    		if(ServerConnSQL.startConn(SqlTextField.getText())){
				stage.close();
				Stage newStage = new Stage();
    			Parent root = null;
    			try 
    			{
    				FXMLLoader loader = new FXMLLoader();
    				loader.setLocation(getClass().getResource("/gui/ServerPanel.fxml"));
    				root =  loader.load(); 
    				Utilities.GenericUtilties.SetWindowMovable(root, newStage);
    				Scene scene = new Scene(root);
    				newStage.initStyle(StageStyle.UNDECORATED);
    				newStage.setScene(scene); 
    				newStage.show();
    			} 
    			catch (IOException e) 
    			{
    				System.out.println("Failed load root");
    				e.printStackTrace();
    			}
    		}
    		else {
    			SqlTextField.setText("");
        		errrorLabel.setVisible(true);	
    		} 
    		
    	}
    }

    @FXML
    void exitPressed(MouseEvent event) {
    	System.exit(0);
    }
    
  
	public void setStage(Stage stage) {
		this.stage= stage;
		
	}

}
