package ProtocolHandler.Operations;

import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class InsertOrderOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof Order)
		{
			Server.SqlServerManager.InsertOrder((Order)data, requestee);
			return true;
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
		return false;
	}

}
