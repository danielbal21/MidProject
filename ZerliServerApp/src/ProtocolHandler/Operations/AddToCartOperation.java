package ProtocolHandler.Operations;

import Entities.ItemInList;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;
import server.ServerApp;

public class AddToCartOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof ItemInList)
		{
			ItemInList newItem = (ItemInList)data;
			Server.SqlServerManager.AddToCart(requestee,newItem.getItem_id(),newItem.getQuantity());
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
