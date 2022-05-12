package ProtocolHandler;

import ProtocolHandler.Operations.*;


public class RequestRegisterer {
	public static void RegisterProtocol()
	{
		/*** Add the mapping between your requests and operations ***/
		Protocol.RegisterOperation(RequestType.Ping, new PingOperation());
		Protocol.RegisterOperation(RequestType.GetAllOrders, new GetAllOrdersOperation());
		Protocol.RegisterOperation(RequestType.UpdateOrder, new UpdateOrderOperation());
		Protocol.RegisterOperation(RequestType.AuthenticateUser, new AuthenticationOperation());
		Protocol.RegisterOperation(RequestType.SetLogOut, new LoggedOutOperation());
		Protocol.RegisterOperation(RequestType.GetCatalog,new GetCatalogOperation());
		Protocol.RegisterOperation(RequestType.GetCart,new GetCartOperation());
		Protocol.RegisterOperation(RequestType.GetBranches, new GetBranchesOperation());
		Protocol.RegisterOperation(RequestType.GetUserCurrency, new GetUserCurrencyOperation());
		Protocol.RegisterOperation(RequestType.UpdateZerliCoins, new UpdateZerliCoinsOperation());
		Protocol.RegisterOperation(RequestType.InsertOrder, new InsertOrderOperation());
	}
}
