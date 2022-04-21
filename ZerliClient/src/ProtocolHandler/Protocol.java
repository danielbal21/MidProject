package ProtocolHandler;

import java.io.IOException;
import java.util.HashMap;
import client.ClientUI;

public class Protocol {
	
	final boolean PASS = true;
	final boolean FAIL = false;
	
	static HashMap<RequestType,IHandler> Handlers = new HashMap<>();
	static HashMap<RequestType,Object> Responses = new HashMap<>();
	boolean onHold = false;
	
	public static void RegisterHandler(RequestType requestType, IHandler handler)
	{
		Handlers.put(requestType, handler);
	}
	public Object GetResponse(RequestType requestType)
	{
		while(onHold) {
			try {Thread.sleep(20);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
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
		Transaction transaction = new Transaction(requestType,"Not yet implemented",data,params,expectingResponse);
		try {
			ClientUI.ClientConnection.sendToServer(transaction);
		} catch (IOException e) {
			e.printStackTrace();
			return FAIL;
		}
		
		return true;
	}

	public boolean Handle(Object msg) {
		if(!onHold || !(msg instanceof Transaction)) return false;
		
		Transaction response = (Transaction)msg;
		Responses.put(response.requestType, Handlers.get(response.getRequestType()).HandleResponse(response.getResponse()));
		onHold = false;
		return PASS;
		
	}
}
