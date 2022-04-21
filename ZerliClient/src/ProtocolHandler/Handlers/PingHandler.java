package ProtocolHandler.Handlers;

import ProtocolHandler.IHandler;

public class PingHandler implements IHandler {

	@Override
	public boolean HandleResponse(Object response) {
		System.out.println((String)response);
		return true;
	}

}
