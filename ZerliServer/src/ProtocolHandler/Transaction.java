package ProtocolHandler;

import java.io.Serializable;

<<<<<<< HEAD
public class Transaction implements Serializable {
=======
public class Transaction implements Serializable{
>>>>>>> refs/heads/master
	//Identifier
	String requestee;
	
	//Protocol Header
	boolean _shouldRespond;
	
	//Data
	RequestType requestType;
	Object received;
	Object params;
	//Response
	Object response;
	public Transaction(RequestType requestType,String requestee,Object data,Object params,boolean returns)
	{
		this.requestType = requestType;
		this.requestee = requestee;
		this.received = data;
		this.params = params;
		this._shouldRespond = returns;
	}
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
