package Entities;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javafx.scene.image.Image;

public class ItemInList implements Serializable{

	private int item_id;
	private int quantity;
	private int price;
	private String itemName;
	private ItemType itemType;
	private CatalogType catalogType;
	private String item_type;
	private String catalog_Type;
	private byte[] image;
	
	public ItemInList() {}
	
	public ItemInList(byte[] image,int item_id, int quantity, int price,String itemName, ItemType itemType, CatalogType catalogType) {
		super();
		this.image=image;
		this.item_id = item_id;
		this.quantity = quantity;
		this.price=price;
		this.itemName = itemName;
		this.itemType = itemType;
		this.catalogType = catalogType;
		
	}	
	public ItemInList(String itemName,CatalogType catalogType, 
			ItemType itemType, int price, int quantity) {
		this.quantity = quantity;
		this.price = price;
		this.itemName = itemName;
		this.itemType = itemType;
		this.catalogType = catalogType;
	}
	public ItemInList(int item_id, int quantity) {
		this.item_id = item_id;
		this.quantity = quantity;
	}
	
	public ItemInList(int item_id, int quantity, String itemName, ItemType itemType, CatalogType catalogType) {
		this.item_id = item_id;
		this.quantity = quantity;
		this.itemName = itemName;
		this.itemType = itemType;
		this.catalogType = catalogType;
	}
	
	public ItemInList(int item_id, int quantity, int price, String itemName, ItemType itemType,
			CatalogType catalogType) {
		this.item_id = item_id;
		this.quantity = quantity;
		this.price = price;
		this.itemName = itemName;
		this.itemType = itemType;
		this.catalogType = catalogType;
	}
	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
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

	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	public CatalogType getCatalogType() {
		return catalogType;
	}
	public void setCatalogType(CatalogType catalogType) {
		this.catalogType = catalogType;
	}
	public String getItem_type() {
		if(itemType==null) return"";
		switch (itemType) {
		case bridal_bouquet: return "Bridal bouquet";
		default:
			String type= itemType.toString();
			String s1 = type.substring(0, 1).toUpperCase();
			String nameCapitalized = s1 + type.substring(1);
			return nameCapitalized;
		}
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCatalog_Type() {
		switch (catalogType) {
			case custom:return "Custom";
			case pre_define:return "Pre define";
			case new_item: return "New item";
		}
		return "";
	}

	public void setCatalog_Type(String catalog_Type) {
		this.catalog_Type = catalog_Type;
	}

	@Override
	public String toString() {
		return "ItemInList [item_id=" + item_id + ", quantity=" + quantity + ", price=" + price + ", itemName="
				+ itemName + ", item_type=" + item_type + ", catalog_Type=" + catalog_Type + "]";
	}

	public Image getJXImage() {
		return new Image(new ByteArrayInputStream(image));
	}
}