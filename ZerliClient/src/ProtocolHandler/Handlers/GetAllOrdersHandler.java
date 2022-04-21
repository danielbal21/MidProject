package ProtocolHandler.Handlers;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GetAllOrdersHandler implements IHandler{

	@Override
	public Object HandleResponse(Object response) {
		if(response instanceof ArrayList<?>) {
			return FXCollections.observableArrayList((ArrayList<Order>)response);
		}
		return null;
		
	}

}
