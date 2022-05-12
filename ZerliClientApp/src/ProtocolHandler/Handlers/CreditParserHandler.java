package ProtocolHandler.Handlers;

import Entities.paymentInfo;
import ProtocolHandler.IHandler;

public class CreditParserHandler implements IHandler {

	@Override
	public Object HandleResponse(Object response) {
		if(response instanceof String[])
		{
			String[] data = (String[])response;
			return new paymentInfo(data[0],data[1],data[2],data[3], data[4], data[5]);
		}
		return null;
	}

}
