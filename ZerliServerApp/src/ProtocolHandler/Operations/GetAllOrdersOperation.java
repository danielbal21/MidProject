package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetAllOrdersOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<Order> allOrders = new ArrayList<Order>();
	    Server.SqlServerManager.getOrders(allOrders);
	    response.SetResponse(allOrders);
		return true;
	}

}
