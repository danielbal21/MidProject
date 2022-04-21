package ProtocolHandler;

public class HandlersRegisterer {
	
	/** Register Response Handlers **/
	
	public static void RegisterHandlers()
	{
		Protocol.RegisterHandler(RequestType.Ping, null);
		Protocol.RegisterHandler(RequestType.GetAllOrders, null);
		Protocol.RegisterHandler(RequestType.UpdateOrder, null);
	}
}
