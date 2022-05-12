package ProtocolHandler;

import ProtocolHandler.Handlers.ArrayListToObservableHandler;
import ProtocolHandler.Handlers.CreditParserHandler;
import ProtocolHandler.Handlers.PingHandler;

public class HandlersRegisterer {
	
	/** Register Response Handlers **/
	
	public static void RegisterHandlers()
	{
		Protocol.RegisterHandler(RequestType.Ping, new PingHandler());
		Protocol.RegisterHandler(RequestType.GetAllOrders, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.UpdateOrder, null);
		Protocol.RegisterHandler(RequestType.AuthenticateUser,null);
		Protocol.RegisterHandler(RequestType.SetLogOut,null);
		Protocol.RegisterHandler(RequestType.GetCatalog,new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.GetCart,new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.GetBranches, new ArrayListToObservableHandler());
		Protocol.RegisterHandler(RequestType.GetUserCurrency, new CreditParserHandler());
		Protocol.RegisterHandler(RequestType.UpdateZerliCoins, null);
		Protocol.RegisterHandler(RequestType.InsertOrder, null);
	}
}
