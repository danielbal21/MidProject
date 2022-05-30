package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

/**
 * The Class DeleteNewItemFromCartOperation.
 */
public class DeleteNewItemFromCartOperation implements IOperation {

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
		if (data instanceof Integer)
		{
			server.Server.SqlServerManager.DeleteNewItemFromCart(requestee,(int)data);
			return true;
		}
		return false;
	}

	/**
	 * This perform is not used since the operation is an Operate-Only request
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		return false;
	}

}
