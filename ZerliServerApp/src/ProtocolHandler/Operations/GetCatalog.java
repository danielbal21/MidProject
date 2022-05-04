package ProtocolHandler.Operations;

import java.util.ArrayList;

import Entities.Item;
import ProtocolHandler.IOperation;
import ProtocolHandler.ResponseWrapper;
import server.Server;

public class GetCatalog implements IOperation{
	@Override
	public boolean Perform(String requestee, Object data, Object params) {
		return false;
	}
	@Override
	public boolean Perform(String requestee, Object data, Object params, ResponseWrapper response) {
		ArrayList<Item> catalogItems = new ArrayList<Item>();
	    Server.SqlServerManager.getCatalogItems(catalogItems);
	    response.SetResponse(catalogItems);
		return false;
	}
}
