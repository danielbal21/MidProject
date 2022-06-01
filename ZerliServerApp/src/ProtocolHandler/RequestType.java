/*
 * 
 */
package ProtocolHandler;
// TODO: Auto-generated Javadoc: Missing a few!

/**
 * A RequestType is an enumeration of all possible requests
 * that can come from the client
 */
public enum RequestType {
	
	/** Updates order data */
	UpdateOrder,
	
	/** Pings - echoes the client transaction
	 * used for connection debugging
	 *  */
	Ping,
	
	/** The Authenticate user is used for
	 * authenticating user credentials and
	 * responding with the correct access level
	 * */
	AuthenticateUser,
	
	/** The SetLogout is used for safely
	 * disconnecting a user
	 * */
	SetLogOut, 
	
	/** The GetCatalog command is used for
	 *  sending a specific catalog
	 * . */
	GetCatalog,
	
	/** Is used for getting a customer saved buying cart 
	 * */
	GetCart,
	
	/** The Get branches is used to retrieve all
	 * available branches. */
	GetBranches,
	
	/** The Get user currency is used
	 * to retrieve a customer's Z-Coins count */
	GetUserCurrency, 
	
	/** The Update zerli coins is used to update Z-Coins count
	 * of a customer */
	UpdateZerliCoins,
	
	/** The Insert order is used to register a new order in the system */
	InsertOrder,
	
	/** The Get branch is used to get a specific branch */
	GetBranch,
	
	/** The Get orders by branch is used to retrieve
	 * all orders of a specific branch */
	GetOrdersByBranch,
	
	/** The Get orders by username is used to retrieve all
	 * orders of a specific customer
	 *  */
	GetOrdersByUsername,
	
	/** The Get items of order is used to retrieve all the items
	 * that were ordered in a specific order */
	GetItemsOfOrder,
	
	/** The Confirm order is used to alter
	 * an order status to confirmed
	 *  */
	ConfirmOrder,
	
	/** The Add to cart is used to update a customers cart
	 * with the recent items. */
	AddToCart,
	
	/** The Get all customer orders is used to retrieve
	 * all of the orders that were made by a customer. */
	GetAllCustomerOrders,
	
	/** The Delete item from cart is used to remove an item from
	 * the saved cart. */
	DeleteItemFromCart,
	
	/** The Get notification is used to retrieve all notification of
	 * a certain user */
	GetNotification,
	
	/** The Update notification is used to update
	 * a notification from read/unread */
	UpdateNotification,
	
	/** The End order is used to complete an order
	 * upon delivery*/
	EndOrder,
	
	/** The Cancel order is used to cancel an order */
	CancelOrder,
	
	/** The Reset new customer is used to reset a new customers data */
	ResetNewCustomer,
	
	/** The Update new item in cart. */
	UpdateNewItemInCart, 
	
	/** The Delete item from new item list. */
	DeleteItemFromNewItemList, 
	
	/** The Delete new item from cart. */
	DeleteNewItemFromCart, 
	
	/** The Delete allnew item from cart. */
	DeleteAllnewItemFromCart,
	
	/** The Remove item by ID is used to remove an item from the catalog
	 * using its ID */
	RemoveItemByID,
	
	/** The Get item by ID is used to retrieve an Item object
	 * using its ID */
	GetItemByID,
	
	/** The Update item is used to update a catalog item data
	 * using its ID */
	UpdateItem,
	/** The Add item is used to add a whole new item to a specific catalog */
	AddItem,
	GetComplaints,
	ComplaintResponse,
	MakeComplaint,
	GetPendingClient,
	RegisterClient,
	GetAllCustomersInfo,
	GetAllEmployeesInfo,
	UpdateAccountAccess,
	UpdateEmployeeRole,
	SaveSurvey,
	GetSurveysNames,
	GetSurvey,
	SaveSurveyAnswers,
	
	/** The Get report is used to get various reports. */
	GetReport,
	
	
	SendNotification,
}
