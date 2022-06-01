package ProtocolHandler.Operations;

import java.time.LocalDate;

import Entities.ReportType;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetReportByBranchOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		if(params instanceof Object[])
		{
			Object[] par = (Object[])params;
			response.SetResponse(Server.SqlServerManager.GetReportOfBranch((ReportType)(par[0]), (boolean)par[1], (String)par[3], 
					java.sql.Date.valueOf(((LocalDate)par[2]))));
			return true;
		}
		return false;
	}

}
