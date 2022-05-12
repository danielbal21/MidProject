package Entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Order implements Serializable {
	
	private String userID;
	
	private String orderID;
	
	private PaymentMethods paymentMethod;
	
	private ShippingMethods shippingMethod;
	
	private Timestamp orderDate;
	
	private Timestamp shippingDate;
	
	private String branchName;
	
	private int totalPrice;
	
	private OrderStatus status;
	
	private ArrayList<ItemInList> items;

	private String greetingCard;
	
	private String city;
	
	private String address;
	
	public Order(String userID, String orderID, PaymentMethods paymentMethod, ShippingMethods shippingtMethod, Timestamp orderDate,
			Timestamp shippingDate, String branchName, int totalPrice, OrderStatus status, ArrayList<ItemInList> items,String city,String address) {
		this.userID = userID;
		this.orderID = orderID;
		this.paymentMethod = paymentMethod;
		this.shippingMethod = shippingtMethod;
		this.orderDate = orderDate;
		this.shippingDate = shippingDate;
		this.branchName = branchName;
		this.totalPrice = totalPrice;
		this.status = status;
		this.items = items;
		this.city = city;
		this.address = address;
	}
	public Order()
	{
		
	}
	
	public String getGreetingCard() {
		return greetingCard;
	}
	public void setGreetingCard(String greetingCard) {
		this.greetingCard = greetingCard;
	}
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public PaymentMethods getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethods paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public ShippingMethods getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethods shippingtMethod) {
		this.shippingMethod = shippingtMethod;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	public void setShippingDate(Timestamp shippingDate) {
		this.shippingDate = shippingDate;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public Timestamp getShippingDate() {
		return shippingDate;
	}
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public ArrayList<ItemInList> getItems() {
		return items;
	}

	public void setItems(ArrayList<ItemInList> items) {
		this.items = items;
	}

}
