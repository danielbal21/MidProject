package ProtocolHandler;

import ProtocolHandler.Handlers.GetAllOrdersHandler;
import ProtocolHandler.Handlers.GetCart;
import ProtocolHandler.Handlers.GetCatalog;
import ProtocolHandler.Handlers.PingHandler;

public class HandlersRegisterer {
	
	/** Register Response Handlers **/
	
	public static void RegisterHandlers()
	{
		Protocol.RegisterHandler(RequestType.Ping, new PingHandler());
		Protocol.RegisterHandler(RequestType.GetAllOrders, new GetAllOrdersHandler());
		Protocol.RegisterHandler(RequestType.UpdateOrder, null);
		Protocol.RegisterHandler(RequestType.AuthenticateUser,null);
		Protocol.RegisterHandler(RequestType.IsLoggedIn,null);
		Protocol.RegisterHandler(RequestType.SetLogOut,null);
		Protocol.RegisterHandler(RequestType.GetCatalog,new GetCatalog());
		Protocol.RegisterHandler(RequestType.GetCart,new GetCart());
	}
}
