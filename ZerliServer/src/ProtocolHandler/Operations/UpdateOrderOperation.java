package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

public class UpdateOrderOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
