package Entities;

import java.io.Serializable;

public class Order implements Serializable{
	
	/*Unique Number for order*/
	private int orderNumber;
	private int price;
	
	/*can be null*/
	private String grettingCard=null;
	private String color;
	
	/*can be null - text for custom order*/
	private String dOrder;
	
	private String shop;
	
	/*Estimated arrive time and date */
	private String date;
	
	/*Order time and date */
	private String orderDate;

	

	public Order(int orderNumber, int price, String grettingCard, String color, String dOrder, String shop, String date,
			String orderDate) {
		this.orderNumber = orderNumber;
		this.price = price;
		this.grettingCard = grettingCard;
		this.color = color;
		this.dOrder = dOrder;
		this.shop = shop;
		this.date = date;
		this.orderDate = orderDate;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getGrettingCard() {
		return grettingCard;
	}

	public void setGrettingCard(String grettingCard) {
		this.grettingCard = grettingCard;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getdOrder() {
		return dOrder;
	}

	public void setdOrder(String dOrder) {
		this.dOrder = dOrder;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", price=" + price + ", grettingCard=" + grettingCard + ", color="
				+ color + ", dOrder=" + dOrder + ", shop=" + shop + ", date=" + date + ", orderDate=" + orderDate + "]";
	}
	
	
}
