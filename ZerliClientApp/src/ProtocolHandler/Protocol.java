package ProtocolHandler;

import java.io.IOException;
import java.util.HashMap;

import client.ClientApp;
import controllers.LoginController;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Protocol {
	
	final boolean PASS = true;
	final boolean FAIL = false;
	final long TIMEOUT = 200000;
	static HashMap<RequestType,IHandler> Handlers = new HashMap<>();
	static HashMap<RequestType,Object> Responses = new HashMap<>();
	Object waiter = new Object();
	boolean onHold = false;
	
	public static void RegisterHandler(RequestType requestType, IHandler handler)
	{
		Handlers.put(requestType, handler);
	}
	
	/** test **/
	Stage stageLoad;
	public void OnLoading()
	{
		 Parent root;
	        try {
	        	FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(getClass().getResource("/gui/mainframes/loading.fxml"));
	            root = loader.load();
	            stageLoad = new Stage();
	            Scene scene = new Scene(root);
	            stageLoad.initStyle(StageStyle.UNDECORATED);
	            stageLoad.setScene(scene); 	
	            stageLoad.show();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public void EndLoading()
	{
		stageLoad.close();
	}
	/** end test **/
	public synchronized Object GetResponse(RequestType requestType)
	{
			//OnLoading();
			int time = 0;
			
			while(onHold && time < TIMEOUT) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			onHold = false;
			if(time > TIMEOUT)
			{
				//System.out.println("timeOut");
				throw new TimeoutException("Command " + requestType.toString() + "did not respond!");
			}
			//EndLoading();
			return Responses.get(requestType);
	}
	
	public Protocol()
	{
		HandlersRegisterer.RegisterHandlers();
	}

	public boolean Invoke(RequestType requestType,Object data,Object params,boolean expectingResponse)
	{
		//waiting for another response
		while(onHold);
		onHold = expectingResponse;
		Transaction transaction = new Transaction(requestType,ClientApp.UserID,data,params,expectingResponse);
		try {
			ClientApp.ClientConnection.sendToServer(transaction);
		} catch (IOException e) {
			e.printStackTrace();
			return FAIL;
		}
		
		return PASS;
	}

	public synchronized boolean Handle(Object msg) {
		
		if(!onHold || !(msg instanceof Transaction)) return false;
		Transaction response = (Transaction)msg;
		if(Handlers.get(response.getRequestType()) != null)
			Responses.put(response.requestType, Handlers.get(response.getRequestType()).HandleResponse(response.getResponse()));
		else
			Responses.put(response.requestType, response.getResponse());
		onHold = false;
		notifyAll();
		return PASS;
		
	}
}
