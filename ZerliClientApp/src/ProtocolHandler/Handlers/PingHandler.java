package ProtocolHandler.Handlers;

import ProtocolHandler.IHandler;

public class PingHandler implements IHandler {

	@Override
	public Object HandleResponse(Object response) {
		return response;
	}

}
