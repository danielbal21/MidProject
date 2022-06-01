package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Survey;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class SavePDFOperation implements IOperation {

	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		if(data instanceof ArrayList<?>)
		{
			if(params instanceof Survey)
			{
				Server.SqlServerManager.SavePDF(requestee,(Survey)params,(ArrayList<byte[]>)data);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		// TODO Auto-generated method stub
		return false;
	}

}
