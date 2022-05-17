package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;
import Entities.*;

public class UpdateNewItemInCartOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof NewItem)
		{
			NewItem newItem= (NewItem)data;
			Server.SqlServerManager.UpdateNewItemInCart(requestee,newItem);
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
