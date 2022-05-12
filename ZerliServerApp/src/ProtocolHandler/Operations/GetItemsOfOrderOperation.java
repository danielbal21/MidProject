package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Item;
import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetItemsOfOrderOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<Item> items = new ArrayList<Item>();
		Order order = (Order)data;
	    Server.SqlServerManager.GetItemsOfOrder(items,  Integer.valueOf(order.getOrderID()));
	    response.SetResponse(items);
		return true;
	}

}
