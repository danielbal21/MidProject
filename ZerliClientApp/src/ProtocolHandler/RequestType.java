package ProtocolHandler;
/** Command Name **/
public enum RequestType {
	GetAllOrders,
	UpdateOrder,
	Ping,
	AuthenticateUser,
	SetLogOut,
	GetCatalog,
	GetCart,
	GetBranches,
	GetUserCurrency, 
	UpdateZerliCoins,
	InsertOrder,
}
