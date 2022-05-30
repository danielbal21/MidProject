package ProtocolHandler.Operations;

import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetBranchesOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		var branches = Server.SqlServerManager.GetBranches();
		response.SetResponse(branches);
		return true;
	}

}
