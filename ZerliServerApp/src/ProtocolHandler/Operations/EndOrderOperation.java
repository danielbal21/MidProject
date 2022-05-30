package ProtocolHandler.Operations;

import Entities.Order;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

/**
 * The Class EndOrderOperation defines the operation
 * that is invoked upon an EndOrder Request.
 */
public class EndOrderOperation implements IOperation {

	/**
	 * The perform operation is the method that is being invoked when the matching request is made
	 * all database calls are managed here, along with some processing and input validation
	 *
	 * @param requestee - the request sender
	 * @param data - the data that was sent
	 * @param params - the parameters constraints that were pinned to the data
	 * @return true, if successful
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		Order order = (Order)data;
		  Server.SqlServerManager.EndOrder(Integer.valueOf(order.getOrderID()),(String)params);
			return true;
	}

	/**
	 * This perform is not used since the operation is an Operate-Only request
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
	  return false;
	}

}
