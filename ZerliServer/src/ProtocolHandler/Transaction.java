package ProtocolHandler;

public class Transaction {
	//Identifer
	String requestee;
	
	//Protocol Header
	boolean _shouldRespond;
	
	//Data
	RequestType requestType;
	Object received;
	Object params;
	//Response
	Object response;
	
	//Getters
	public String getRequester()
	{
		return requestee;
	}
	
	public RequestType getRequestType()
	{
		return requestType;
	}
	
	public boolean shouldRespond()
	{
		return _shouldRespond;
	}
	
	public Object getReceivedData()
	{
		return received;
	}
	public Object getReceivedParameters()
	{
		return params;
	}
	public Object getResponse()
	{
		return response;
	}
	public void setResponse(Object res)
	{
		response = res;
	}
}
