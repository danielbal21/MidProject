package ProtocolHandler;

import ProtocolHandler.Handlers.ArrayListToObservableHandler;
import ProtocolHandler.Handlers.PingHandler;

public class HandlersRegisterer {
	
	/** Register Response Handlers **/
	
	public static void RegisterHandlers()
	{
		Protocol.RegisterHandler(RequestType.Ping, new PingHandler());
		Protocol.RegisterHandler(RequestType.GetAllOrders, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.UpdateOrder, null);
		Protocol.RegisterHandler(RequestType.AuthenticateUser,null);
		Protocol.RegisterHandler(RequestType.GetBranches, new ArrayListToObservableHandler());
	}
}
