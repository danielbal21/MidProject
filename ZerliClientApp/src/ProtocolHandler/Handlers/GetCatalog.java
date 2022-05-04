package ProtocolHandler.Handlers;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IHandler;
import javafx.collections.FXCollections;

public class GetCatalog implements IHandler {

	@Override
	public Object HandleResponse(Object response) {
		if(response instanceof ArrayList<?>) {
			return FXCollections.observableArrayList((ArrayList<Order>)response);
		}
		return null;
	}

}
