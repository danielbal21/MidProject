package ProtocolHandler;


import ProtocolHandler.Operations.AuthenticationOperation;
import ProtocolHandler.Operations.GetAllOrdersOperation;
import ProtocolHandler.Operations.GetCart;
import ProtocolHandler.Operations.GetCatalog;
import ProtocolHandler.Operations.LoggedInOperation;
import ProtocolHandler.Operations.LoggedOutOperation;
import ProtocolHandler.Operations.PingOperation;
import ProtocolHandler.Operations.UpdateOrderOperation;

public class RequestRegisterer {
	public static void RegisterProtocol()
	{
		/*** Add the mapping between your requests and operations ***/
		Protocol.RegisterOperation(RequestType.Ping, new PingOperation());
		Protocol.RegisterOperation(RequestType.GetAllOrders, new GetAllOrdersOperation());
		Protocol.RegisterOperation(RequestType.UpdateOrder, new UpdateOrderOperation());
		Protocol.RegisterOperation(RequestType.AuthenticateUser, new AuthenticationOperation());
		Protocol.RegisterOperation(RequestType.IsLoggedIn, new LoggedInOperation());
		Protocol.RegisterOperation(RequestType.SetLogOut, new LoggedOutOperation());
		Protocol.RegisterOperation(RequestType.GetCatalog,new GetCatalog());
		Protocol.RegisterOperation(RequestType.GetCart,new GetCart());


	}
}
