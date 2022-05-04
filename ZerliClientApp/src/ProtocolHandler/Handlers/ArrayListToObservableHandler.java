package ProtocolHandler.Handlers;

import java.util.ArrayList;

import Entities.Order;
import ProtocolHandler.IHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArrayListToObservableHandler implements IHandler{

	@Override
	public Object HandleResponse(Object response) {
		if(response instanceof ArrayList<?>) {
			return FXCollections.observableArrayList((ArrayList<?>)response);
		}
		return null;
	}

}
