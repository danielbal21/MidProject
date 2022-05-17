package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;

public class DeleteItemFromNewItemListOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}
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
