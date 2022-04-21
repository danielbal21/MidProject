package ProtocolHandler;

public class ResponseWrapper {
	Object response;
	
	public ResponseWrapper(Object response)
	{
		this.response = response;
	}
	
	public ResponseWrapper()
	{
		response = null;
	}
	
	public void SetResponse(Object Response)
	{
		response = Response;
	}
	
	public Object getResponse()
	{
		return response;
	}
}
