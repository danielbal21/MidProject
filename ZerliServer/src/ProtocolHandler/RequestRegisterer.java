package ProtocolHandler;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;

import ProtocolHandler.Operations.GetAllOrdersOperation;
import ProtocolHandler.Operations.PingOperation;
import ProtocolHandler.Operations.UpdateOrderOperation;

public class RequestRegisterer {
	public static void RegisterProtocol()
	{
		/*** Add the mapping between your requests and operations ***/
		Protocol.RegisterOperation(RequestType.Ping, new PingOperation());
		Protocol.RegisterOperation(RequestType.GetAllOrders, new GetAllOrdersOperation());
		Protocol.RegisterOperation(RequestType.UpdateOrder, new UpdateOrderOperation());
	}
}
