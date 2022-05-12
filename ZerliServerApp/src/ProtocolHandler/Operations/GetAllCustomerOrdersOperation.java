package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.*;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;
import server.ServerApp;

public class GetAllCustomerOrdersOperation implements IOperation{

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<Order> customerOrders = new ArrayList<>();
		Server.SqlServerManager.GetAllCustomerOrders(requestee,customerOrders);
		System.out.println(customerOrders);
		response.SetResponse(customerOrders);
		return true;
	}

}
