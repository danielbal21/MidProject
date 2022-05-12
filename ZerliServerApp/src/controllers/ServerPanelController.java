package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.ClientInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ocsf.server.ConnectionToClient;
import server.Server;
import server.ServerApp;

public class ServerPanelController implements Initializable{
	
 	@FXML
	private ImageView exitBtn;
 	
    @FXML
    private TextArea console;

    @FXML
    private Label dbStatusServer;
    
    @FXML
    private Label serverStatusLbl;
    
    @FXML
    private TableView<ClientInfo> clientTable;

    @FXML
    private TableColumn<ClientInfo, String> ipCol;
    
    @FXML
    private TableColumn<ClientInfo, String> hostCol;

    @FXML
    private TableColumn<ClientInfo, String> statusCol;

    ObservableList<ClientInfo> clientsInfo;
    
	private Stage stage;
   
	
	 @FXML
     void exitPressed(MouseEvent event) {
	 try {
    		ServerApp.server.close();
		} catch (IOException e) {
			System.out.println("Falied close Server");
			e.printStackTrace();
		}
    	System.exit(0);
    }
 



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));	
		hostCol.setCellValueFactory(new PropertyValueFactory<>("hostName"));	
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));	
		console.setEditable(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					clientsInfo = FXCollections.observableArrayList();
					for (int i= 0; i<ServerApp.server.getNumberOfClients();i++) {
						String ip = ((ConnectionToClient)ServerApp.server.getClientConnections()[i]).getInetAddress().getHostAddress();
			    		String host = ((ConnectionToClient)ServerApp.server.getClientConnections()[i]).getInetAddress().getHostName();
						clientsInfo.add(new ClientInfo(ip, host, "Active"));
					}
					clientTable.setItems(clientsInfo);
					try {Thread.sleep(2000);} 
					catch (InterruptedException e) {e.printStackTrace();}
					//Server.Log("Server", "Updating Connections List");
				}
			}
		}).start();
		Server.Console = console;
		Server.Log("Application", "Loading Application version " + Server.VERSION);
		Server.Log("Database", "is connected");
		Server.Log("Server", "is connected on port: " + Server.DEFAULT_PORT) ;
	}
}

