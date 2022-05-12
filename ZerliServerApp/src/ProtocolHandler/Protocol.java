package ProtocolHandler;

import java.io.IOException;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;
import server.Server;

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
		Server.Log("Server", "Message received from: " + sender.getInetAddress().getHostAddress());
		ResponseWrapper response = new ResponseWrapper();
		if(!(transmission instanceof Transaction))
		{
			Server.Log("Protocol", "Unconventional data received, ignoring...");
			throw new Exception("Unconventional Transimission");
		}
		
		Transaction transaction = (Transaction)transmission;
		boolean status;
		Server.Log("Protocol",String.format("Incoming transmission from %s (%s)\n-request:%s\nresponse? %s",transaction.requestee,sender.getInetAddress().getHostAddress(),transaction.requestType.toString(),transaction.shouldRespond() ? "Yes":"No"));
		if(transaction.shouldRespond())
			status = Operations.get(transaction.getRequestType()).Perform(transaction.requestee, transaction.getReceivedData(), transaction.getReceivedParameters(),response);
		else
			status = Operations.get(transaction.getRequestType()).Perform(transaction.requestee, transaction.getReceivedData(), transaction.getReceivedParameters());
		
		if(FAIL == status)
		{
			Server.Log("Protocol", "Operation of request " + transaction.requestType + " for " + transaction.getRequester() + "has failed");
			return FAIL;
		}
		if(transaction.shouldRespond())
		{
			Server.Log("Protocol", "Building response transaction for " + transaction.requestType + " for " + transaction.getRequester());
			transaction.setResponse(response.getResponse());
			Server.Log("Server", "Sending response to client: " + transaction.getRequester());
			send(sender,transaction);
		}
		
		return PASS;
	}
	
	private void send(ConnectionToClient client,Transaction trans) throws Exception
	{
	    try {
			client.sendToClient(trans);
		} catch (IOException e) {
			Server.Log("Server", "Failed connecting to client: " + trans.getRequester());
			throw new Exception("Cannot reply to client : " + e.getMessage() +e.getStackTrace().toString());
		}
	}
}
