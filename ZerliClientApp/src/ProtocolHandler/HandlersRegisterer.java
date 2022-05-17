package ProtocolHandler;

import ProtocolHandler.Handlers.ArrayListToObservableHandler;
import ProtocolHandler.Handlers.CreditParserHandler;
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
		Protocol.RegisterHandler(RequestType.GetUserCurrency, new CreditParserHandler());
		Protocol.RegisterHandler(RequestType.UpdateZerliCoins, null);
		Protocol.RegisterHandler(RequestType.InsertOrder, null);
		Protocol.RegisterHandler(RequestType.GetBranch, null);
		Protocol.RegisterHandler(RequestType.GetOrdersByBranch, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.GetItemsOfOrder, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.ConfirmOrder, null);
		
		Protocol.RegisterHandler(RequestType.AddToCart, null);
		Protocol.RegisterHandler(RequestType.GetAllCustomerOrders, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.DeleteItemFromCart, null);
		Protocol.RegisterHandler(RequestType.GetNotification, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.UpdateNotification,null);
		
		Protocol.RegisterHandler(RequestType.EndOrder,null);
		
	}
}
