package ProtocolHandler;
/** Command Name **/
public enum RequestType {
	UpdateOrder,
	Ping,
	AuthenticateUser,
	SetLogOut, 
	GetCatalog,
	GetCart,
	GetBranches,
	GetManagerBranch,
	GetOrdersByBranch,
	GetOrdersByUsername,
	GetItemsOfOrder,
	ConfirmOrder,
}
