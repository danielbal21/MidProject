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
	GetBranch,
	GetOrdersByBranch,
	GetOrdersByUsername,
	GetItemsOfOrder,
	ConfirmOrder,
	AddToCart,
	GetAllCustomerOrders,
	DeleteItemFromCart,
	GetNotification,
	UpdateNotification,
	EndOrder,
	CancelOrder,
	ResetNewCustomer,
	UpdateNewItemInCart, 
	DeleteItemFromNewItemList, 
	DeleteNewItemFromCart, 
	DeleteAllnewItemFromCart,
	RemoveItemByID,
	GetItemByID,
	UpdateItem,
	AddItem,
	GetPendingClient,
	RegisterClient,
}
