package ProtocolHandler.Operations;

import Entities.Item;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class AddItemOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		Server.SqlServerManager.InsertItem((Item)data);;
		return true;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
		return false;
	}

}
