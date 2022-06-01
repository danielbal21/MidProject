package ProtocolHandler.Handlers;

import java.util.ArrayList;

import ProtocolHandler.IHandler;

public class imageFromServerHandler implements IHandler {

	@Override
	public Object HandleResponse(Object response) {
		if(response instanceof  ArrayList<?> )
		{
		ArrayList<byte[]> listOfImages= (ArrayList<byte[]> )response;
		 return listOfImages;
		}
		return null;
	}

}
