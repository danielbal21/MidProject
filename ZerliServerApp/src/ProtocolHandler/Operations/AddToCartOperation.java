package ProtocolHandler.Operations;

import Entities.*;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class AddToCartOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {

		return false;
	}
		

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		if(data instanceof ItemInList)
		{	
			ItemInList newItem = (ItemInList)data;
			response.SetResponse(Server.SqlServerManager.AddToCart(requestee,newItem.getItem_id(),newItem.getQuantity()));
			return true;
		}
		else {
			return false;
		}
	}
}
