package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

/**
 * The Class DeleteItemFromNewItemListOperation defines the operation
 * that is invoked upon an DeleteItemFromNewItem Request.
 */
public class DeleteItemFromNewItemListOperation implements IOperation {

	/**
	 * This perform is not used since the operation is an Operate&Respond request
	 */
	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}
	
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
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) 
	{
		if (data instanceof Integer && params instanceof Integer )
		{
			server.Server.SqlServerManager.DeleteItemFromNewItemList((int)data,(int)params);
			response.SetResponse(true);
			return true;
		}
		return false;
	}
}