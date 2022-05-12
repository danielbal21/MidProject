package ProtocolHandler;

import ProtocolHandler.Handlers.ArrayListToObservableHandler;

import ProtocolHandler.Handlers.PingHandler;

public class HandlersRegisterer {
	
	/** Register Response Handlers **/
	
	public static void RegisterHandlers()
	{
		Protocol.RegisterHandler(RequestType.Ping, new PingHandler());
		Protocol.RegisterHandler(RequestType.UpdateOrder, null);
		Protocol.RegisterHandler(RequestType.AuthenticateUser,null);
		Protocol.RegisterHandler(RequestType.SetLogOut,null);
		Protocol.RegisterHandler(RequestType.GetCatalog,new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.GetCart,new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.GetBranches, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.GetManagerBranch, null);
		Protocol.RegisterHandler(RequestType.GetOrdersByBranch, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.GetItemsOfOrder, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.ConfirmOrder, null);
		
		Protocol.RegisterHandler(RequestType.AddToCart, null);
		Protocol.RegisterHandler(RequestType.GetAllCustomerOrders, new ArrayListToObservableHandler());
	}
}
