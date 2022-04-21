package ProtocolHandler;

public interface IOperation {
	
	public boolean Perform(String requestee,Object data, Object params);
	public boolean Perform(String requestee,Object data,Object params,ResponseWrapper response);
}
