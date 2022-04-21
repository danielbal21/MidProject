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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ocsf.server.ConnectionToClient;
import server.ServerUI;

public class ServerPanelController implements Initializable{
	
    @FXML
    private Button exitBtn;
   
    
    @FXML
    private TableView<ClientInfo> clientTable;

    @FXML
    private TableColumn<ClientInfo, String> ipCol;
    
    @FXML
    private TableColumn<ClientInfo, String> hostCol;

    @FXML
    private TableColumn<ClientInfo, String> statusCol;

    ObservableList<ClientInfo> clientsInfo;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));	
		hostCol.setCellValueFactory(new PropertyValueFactory<>("hostName"));	
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					clientsInfo = FXCollections.observableArrayList();
					for (int i= 0; i<ServerUI.server.getNumberOfClients();i++) {
						byte[] ip = ((ConnectionToClient)ServerUI.server.getClientConnections()[i]).getInetAddress().getAddress();
			    		String host = ((ConnectionToClient)ServerUI.server.getClientConnections()[i]).getInetAddress().getHostName();
						clientsInfo.add(new ClientInfo(ip, host, "Active"));
					}
					clientTable.setItems(clientsInfo);
					try {Thread.sleep(2000);} 
					catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		}).start();
	}
   
    @FXML
    void exitPressed(ActionEvent event) {
    	try {
    		ServerUI.server.close();
		} catch (IOException e) {
			System.out.println("Falied close Server");
			e.printStackTrace();
		}
    	System.exit(0);
    }
}

