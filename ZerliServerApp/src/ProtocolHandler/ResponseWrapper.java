/*
 * 
 */
package ProtocolHandler;

/**
 * The Class ResponseWrapper is used to return a response
 * from an IOperationd
 */
public class ResponseWrapper {
	
	/** The response to be returned. */
	Object response;
	
	/**
	 * Instantiates a new response wrapper.
	 *
	 * @param The response to be set
	 */
	public ResponseWrapper(Object response)
	{
		this.response = response;
	}
	
	/**
	 * Instantiates a new response wrapper.
	 */
	public ResponseWrapper()
	{
		response = null;
	}
	
	/**
	 * Sets the response.
	 *
	 * @param The response to be set
	 */
	public void SetResponse(Object Response)
	{
		response = Response;
	}
	
	/**
	 * Gets the response.
	 *
	 * @return The Response
	 */
	public Object getResponse()
	{
		return response;
	}
}
