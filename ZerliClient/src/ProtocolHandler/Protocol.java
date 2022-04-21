package ProtocolHandler;

import java.io.IOException;
import java.util.HashMap;

import client.Client;

public class Protocol {
	
	final boolean PASS = true;
	final boolean FAIL = false;
	
	static HashMap<RequestType,IHandler> Handlers = new HashMap<>();
	boolean onHold = false;
	
	public static void RegisterHandler(RequestType requestType, IHandler handler)
	{
		Handlers.put(requestType, handler);
	}
	
	public Protocol()
	{
		HandlersRegisterer.RegisterHandlers();
	}

	public boolean Invoke(RequestType requestType,Object data,Object params,boolean expectingResponse)
	{
		//waiting for another response
		while(onHold);
		
		Transaction transaction = new Transaction(requestType,"Not yet implemented",data,params,expectingResponse);
		onHold = false;
		try {
			Client.ClientConnection.sendToServer(transaction);
		} catch (IOException e) {
			e.printStackTrace();
			return FAIL;
		}
		
		return true;
	}

	public boolean Handle(Object msg) {
		if(!onHold || !(msg instanceof Transaction)) return false;
		
		onHold = false;
		Transaction response = (Transaction)msg;
		return Handlers.get(response.getRequestType()).HandleResponse(response.getResponse());
		
	}
}
