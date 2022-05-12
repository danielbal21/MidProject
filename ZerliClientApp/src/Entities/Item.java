package Entities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javafx.scene.image.Image;

public class Item implements Serializable {
	private int id;
	private String name;
	private int price;
	private  CatalogType catalogType ;
	private ItemType itemType;
	private Color color;
	private byte[] image;
	
	private int quantity;
	
	public Item( int id, String name,int price, CatalogType catalogType, ItemType itemType, Color color, byte[] image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.catalogType = catalogType;
		this.itemType = itemType;
		this.color = color;
		this.image =image;
	}
	
	public Item( int id, String name,int price, CatalogType catalogType, ItemType itemType, Color color) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.catalogType = catalogType;
		this.itemType = itemType;
		this.color = color;
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public CatalogType getCatalogType() {
		return catalogType;
	}
	public void setCatalogType(CatalogType catalogType) {
		this.catalogType = catalogType;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;	
	}
	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Image getJXImage() {
		// TODO Auto-generated method stub
		return new Image(new ByteArrayInputStream(image));
	}
}