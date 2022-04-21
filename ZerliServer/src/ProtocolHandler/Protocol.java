package ProtocolHandler;

import java.io.IOException;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;

public class Protocol {
	final boolean PASS = true;
	final boolean FAIL = false;
	
	static HashMap<RequestType,IOperation> Operations = new HashMap<>();
	
	public static void RegisterOperation(RequestType type, IOperation op)
	{
		Operations.put(type, op);
	}
	
	public Protocol()
	{
		RequestRegisterer.RegisterProtocol();
	}
	public boolean Handle(Object transmission, ConnectionToClient sender) throws Exception
	{
		ResponseWrapper response = new ResponseWrapper();
		if(!(transmission instanceof Transaction))
			throw new Exception("Unconventional Transimission");
		
		Transaction transaction = (Transaction)transmission;
		boolean status;

		
		if(transaction.shouldRespond())
			status = Operations.get(transaction.getRequestType()).Perform(transaction.requestee, transaction.getReceivedData(), transaction.getReceivedParameters(),response);
		else
			status = Operations.get(transaction.getRequestType()).Perform(transaction.requestee, transaction.getReceivedData(), transaction.getReceivedParameters());
		
		if(FAIL == status)
			return FAIL;
		if(transaction.shouldRespond())
		{
			transaction.setResponse(response.getResponse());
			send(sender,transaction);
		}
		
		return PASS;
	}
	
	private void send(ConnectionToClient client,Transaction trans) throws Exception
	{
	    try {
			client.sendToClient(trans);
		} catch (IOException e) {
			throw new Exception("Cannot reply to client : " + e.getStackTrace().toString());
		}
	}
}
