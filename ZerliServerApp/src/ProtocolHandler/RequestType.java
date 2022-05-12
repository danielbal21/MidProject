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
	GetUserCurrency, 
	UpdateZerliCoins,
	InsertOrder,
	GetManagerBranch,
	GetOrdersByBranch,
	GetOrdersByUsername,
	GetItemsOfOrder,
	ConfirmOrder,
	AddToCart,
	GetAllCustomerOrders,
	DeleteItemFromCart,
	GetNotification,
	UpdateNotification,
}
