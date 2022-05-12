package Entities;

import java.io.Serializable;

public class ItemInList implements Serializable{

	private int item_id;
	private int quantity;
	private int price;
	private String item_name;
	private ItemType item_type;
	private CatalogType catalog_type;
	
	public ItemInList() {
		
	}
			
	public ItemInList(String item_name,CatalogType catalog_type, 
			ItemType item_type, int price, int quantity) {
		this.quantity = quantity;
		this.price = price;
		this.item_name = item_name;
		this.item_type = item_type;
		this.catalog_type = catalog_type;
	}

	public ItemInList(int item_id, int quantity) {
		this.item_id = item_id;
		this.quantity = quantity;
	}
	
	public ItemInList(int item_id, int quantity, String item_name, ItemType item_type, CatalogType catalog_type) {
		this.item_id = item_id;
		this.quantity = quantity;
		this.item_name = item_name;
		this.item_type = item_type;
		this.catalog_type = catalog_type;
	}
	
	public ItemInList(int item_id, int quantity, int price, String item_name, ItemType item_type,
			CatalogType catalog_type) {
		this.item_id = item_id;
		this.quantity = quantity;
		this.price = price;
		this.item_name = item_name;
		this.item_type = item_type;
		this.catalog_type = catalog_type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public ItemType getItem_type() {
		return item_type;
	}

	public void setItem_type(ItemType item_type) {
		this.item_type = item_type;
	}

	public CatalogType getCatalog_type() {
		return catalog_type;
	}

	public void setCatalog_type(CatalogType catalog_type) {
		this.catalog_type = catalog_type;
	}

	@Override
	public String toString() {
		return "ItemInList [quantity=" + quantity + ", price=" + price + ", item_name=" + item_name + ", item_type="
				+ item_type + ", catalog_type=" + catalog_type + "]";
	}
	
	

}
