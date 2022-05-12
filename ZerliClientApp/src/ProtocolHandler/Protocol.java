package ProtocolHandler;

import java.io.IOException;
import java.util.HashMap;

import client.ClientApp;

public class Protocol {
	
	final boolean PASS = true;
	final boolean FAIL = false;
	final long TIMEOUT = 20000;
	static HashMap<RequestType,IHandler> Handlers = new HashMap<>();
	static HashMap<RequestType,Object> Responses = new HashMap<>();
	boolean onHold = false;
	
	public static void RegisterHandler(RequestType requestType, IHandler handler)
	{
		Handlers.put(requestType, handler);
	}
	public Object GetResponse(RequestType requestType)
	{
		int time = 0;
		while(onHold && time < TIMEOUT) {
			try {
				Thread.sleep(20);
				time += 20;
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		onHold = false;
			if(time > TIMEOUT)
			{
				//System.out.println("timeOut");
				throw new TimeoutException("Command " + requestType.toString() + "did not respond!");
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
		Transaction transaction = new Transaction(requestType,ClientApp.UserID,data,params,expectingResponse);
		try {
			ClientApp.ClientConnection.sendToServer(transaction);
		} catch (IOException e) {
			e.printStackTrace();
			return FAIL;
		}
		
		return PASS;
	}

	public boolean Handle(Object msg) {
		
		if(!onHold || !(msg instanceof Transaction)) return false;
		
		Transaction response = (Transaction)msg;
		if(Handlers.get(response.getRequestType()) != null)
			Responses.put(response.requestType, Handlers.get(response.getRequestType()).HandleResponse(response.getResponse()));
		else
			Responses.put(response.requestType, response.getResponse());
		onHold = false;
		return PASS;
		
	}
}
