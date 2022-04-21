package ProtocolHandler;

import ProtocolHandler.Operations.PingOperation;

public class RequestRegisterer {
	public static void RegisterProtocol()
	{
		/*** Add the mapping between your requests and operations ***/
		Protocol.RegisterOperation(RequestType.Ping, new PingOperation());
		Protocol.RegisterOperation(RequestType.GetAllOrders, null);
		Protocol.RegisterOperation(RequestType.UpdateOrder, null);
	}
}
