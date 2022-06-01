package Entities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javafx.scene.image.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class Item.
 */
public class Item implements Serializable {
	
	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
	
	/** The price. */
	private int price;
	
	/** The catalog type. */
	private  CatalogType catalogType ;
	
	/** The item type. */
	private ItemType itemType;
	
	/** The color. */
	private Color color;
	
	/** The image. */
	private byte[] image;
	
	/** The is on sale. */
	private boolean isOnSale = false;
	
	/** The sale price. */
	private int salePrice = -1;
	
	/**
	 * Instantiates a new item.
	 *
	 * @param id the id
	 * @param name the name
	 * @param price the price
	 * @param catalogType the catalog type
	 * @param itemType the item type
	 * @param color the color
	 * @param image the image
	 * @param isOnSale the is on sale
	 * @param salePrice the sale price
	 */
	public Item( int id, String name,int price, CatalogType catalogType, ItemType itemType, Color color, byte[] image,boolean isOnSale, int salePrice) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.catalogType = catalogType;
		this.itemType = itemType;
		this.color = color;
		this.image =image;
		this.salePrice = salePrice;
		this.isOnSale = isOnSale;
	}
	
	/**
	 * Checks if is on sale.
	 *
	 * @return true, if is on sale
	 */
	public boolean isOnSale() {
		return isOnSale;
	}
	
	/**
	 * Sets the on sale.
	 *
	 * @param isOnSale the new on sale
	 */
	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}
	
	/**
	 * Gets the sale price.
	 *
	 * @return the sale price
	 */
	public int getSalePrice() {
		return salePrice;
	}
	
	/**
	 * Sets the sale price.
	 *
	 * @param salePrice the new sale price
	 */
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Gets the current price.
	 *
	 * @return the current price
	 */
	public int getCurrentPrice()
	{
		return isOnSale? salePrice : price;
	}
	
	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/**
	 * Gets the catalog type.
	 *
	 * @return the catalog type
	 */
	public CatalogType getCatalogType() {
		return catalogType;
	}
	
	/**
	 * Sets the catalog type.
	 *
	 * @param catalogType the new catalog type
	 */
	public void setCatalogType(CatalogType catalogType) {
		this.catalogType = catalogType;
	}
	
	/**
	 * Gets the item type.
	 *
	 * @return the item type
	 */
	public ItemType getItemType() {
		return itemType;
	}
	
	/**
	 * Sets the item type.
	 *
	 * @param itemType the new item type
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(Color color) {
		this.color = color;	
	}
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}
	
	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	/**
	 * Gets the JX image.
	 *
	 * @return the JX image
	 */
	public Image getJXImage() {
		return new Image(new ByteArrayInputStream(image));
	}
}