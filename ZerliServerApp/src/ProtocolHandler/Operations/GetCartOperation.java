package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.*;
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
		ArrayList<ItemInList> cartItems = new ArrayList<ItemInList>();
	    Server.SqlServerManager.getCartItems(requestee,cartItems);
	    response.SetResponse(cartItems);
		return true;
	}

}
