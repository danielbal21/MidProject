package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

/**
 * The Class CancelOrderOperation defines the operation
 * that is invoked upon a CancelOrder Request.
 */
public class CancelOrderOperation implements IOperation{

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
		if(data instanceof Integer && params instanceof Integer ) {
			Server.SqlServerManager.cancelOrder((Integer)data,(Integer)params);
			return true;
		}
		else return false;
	}

	/**
	 * This perform is not used since the operation is an Operate-Only request
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}

