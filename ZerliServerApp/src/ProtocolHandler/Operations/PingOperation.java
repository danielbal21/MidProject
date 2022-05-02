package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

public class PingOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		response.SetResponse("Hi " + requestee + "You sent: " + data + "with params: " + params);
		return true;
	}
	
}
