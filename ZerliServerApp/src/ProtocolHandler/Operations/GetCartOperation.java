package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Item;
import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetCartOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<Item> cartItems = new ArrayList<Item>();
	    Server.SqlServerManager.getCartItems(cartItems);
	    response.SetResponse(cartItems);
		return true;
	}

}
