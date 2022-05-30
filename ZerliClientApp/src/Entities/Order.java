package Entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;

import Utilities.GenericUtilties;

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
	
	private String fullname;
	
	private String phone;
	
	private String order_date;
	private String shipping_date;
	private String payment_method;
	private String shipping_method;
	private String order_status;
	
	public Order(String userID, String orderID, PaymentMethods paymentMethod, 
			ShippingMethods shippingtMethod, Timestamp orderDate,
			Timestamp shippingDate, String branchName, int totalPrice, 
			OrderStatus status, ArrayList<ItemInList> items,String city,String address) {
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
	public void setShippingMethod(ShippingMethods shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	public Timestamp getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Timestamp shippingDate) {
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
	public String getGreetingCard() {
		return greetingCard;
	}
	public void setGreetingCard(String greetingCard) {
		this.greetingCard = greetingCard;
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
	
	public String getOrder_date() {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(GenericUtilties.Convert_LocalDate_To_SQLDate(orderDate));
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	
	public String getShipping_date() {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(GenericUtilties.Convert_LocalDate_To_SQLDate(shippingDate));
		
	}	
	
	public void setShipping_date(String shipping_date) {
		this.shipping_date = shipping_date;
	}
	
	public String getPayment_method() {
		switch(getPaymentMethod()){
		case credit_card:  return "Credit card";
		case zerli_coins:  return "Zerli coins";
		default: break;
		}
		return null;
	}
	
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getShipping_method() {
		switch(getShippingMethod()){
		case shipping:  return "Shipping";
		case pickup:  return "Pickup";
		default: break;
	}
	return null;
	}
	public void setShipping_method(String shipping_method) {
		this.shipping_method = shipping_method;
	}
	public String getOrder_status() {
		switch(getStatus()){
		case pending_confirm:  return "Pending confirm";
		case pending_cancel:  return "Pending cancel";
		case confirmed: return "Confirmed";
		case canceled:return  "Canceled" ;
		case completed:return "Completed";
		default: break;
	}
	return null;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Order [userID=" + userID + ", orderID=" + orderID + ", paymentMethod=" + paymentMethod
				+ ", shippingMethod=" + shippingMethod + ", orderDate=" + orderDate + ", shippingDate=" + shippingDate
				+ ", branchName=" + branchName + ", totalPrice=" + totalPrice + ", status=" + status + ", items="
				+ items + ", greetingCard=" + greetingCard + ", city=" + city + ", address=" + address + "]";
	}
	


}
