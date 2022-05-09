package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Order  {
	
	private String userID;
	
	private String orderID;
	
	private String paymentMethod;
	
	private String shippingtMethod;
	
	private String orderDate;
	
	private String shippingDate;
	
	private String branchName;
	
	private int totalPrice;
	
	private String status;
	
	private ArrayList<Item> items;

	
	public Order(String userID, String orderID, String paymentMethod, String shippingtMethod, String orderDate,
			String shippingDate, String branchName, int totalPrice, String status, ArrayList<Item> items) {
		this.userID = userID;
		this.orderID = orderID;
		this.paymentMethod = paymentMethod;
		this.shippingtMethod = shippingtMethod;
		this.orderDate = orderDate;
		this.shippingDate = shippingDate;
		this.branchName = branchName;
		this.totalPrice = totalPrice;
		this.status = status;
		this.items = items;
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

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getShippingtMethod() {
		return shippingtMethod;
	}

	public void setShippingtMethod(String shippingtMethod) {
		this.shippingtMethod = shippingtMethod;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}


	
	

	
}
