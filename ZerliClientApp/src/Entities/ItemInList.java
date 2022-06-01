package Entities;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javafx.scene.image.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class ItemInList.
 */
public class ItemInList implements Serializable{

	/** The item id. */
	private int item_id;
	
	/** The quantity. */
	private int quantity;
	
	/** The price. */
	private int price;
	
	/** The item name. */
	private String itemName;
	
	/** The item type. */
	private ItemType itemType;
	
	/** The catalog type. */
	private CatalogType catalogType;
	
	/** The item type. */
	private String item_type;
	
	/** The catalog type. */
	private String catalog_Type;
	
	/** The image. */
	private byte[] image;
	
	/**
	 * Instantiates a new item in list.
	 */
	public ItemInList() {}
	
	/**
	 * Instantiates a new item in list.
	 *
	 * @param image the image
	 * @param item_id the item id
	 * @param quantity the quantity
	 * @param price the price
	 * @param itemName the item name
	 * @param itemType the item type
	 * @param catalogType the catalog type
	 */
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
	
	/**
	 * Instantiates a new item in list.
	 *
	 * @param itemName the item name
	 * @param catalogType the catalog type
	 * @param itemType the item type
	 * @param price the price
	 * @param quantity the quantity
	 */
	public ItemInList(String itemName,CatalogType catalogType, 
			ItemType itemType, int price, int quantity) {
		this.quantity = quantity;
		this.price = price;
		this.itemName = itemName;
		this.itemType = itemType;
		this.catalogType = catalogType;
	}
	
	/**
	 * Instantiates a new item in list.
	 *
	 * @param item_id the item id
	 * @param quantity the quantity
	 */
	public ItemInList(int item_id, int quantity) {
		this.item_id = item_id;
		this.quantity = quantity;
	}
	
	/**
	 * Instantiates a new item in list.
	 *
	 * @param item_id the item id
	 * @param quantity the quantity
	 * @param itemName the item name
	 * @param itemType the item type
	 * @param catalogType the catalog type
	 */
	public ItemInList(int item_id, int quantity, String itemName, ItemType itemType, CatalogType catalogType) {
		this.item_id = item_id;
		this.quantity = quantity;
		this.itemName = itemName;
		this.itemType = itemType;
		this.catalogType = catalogType;
	}
	
	/**
	 * Instantiates a new item in list.
	 *
	 * @param item_id the item id
	 * @param quantity the quantity
	 * @param price the price
	 * @param itemName the item name
	 * @param itemType the item type
	 * @param catalogType the catalog type
	 */
	public ItemInList(int item_id, int quantity, int price, String itemName, ItemType itemType,
			CatalogType catalogType) {
		this.item_id = item_id;
		this.quantity = quantity;
		this.price = price;
		this.itemName = itemName;
		this.itemType = itemType;
		this.catalogType = catalogType;
	}
	
	/**
	 * Instantiates a new item in list.
	 *
	 * @param item_id the item id
	 * @param itemName the item name
	 * @param quantity the quantity
	 */
	public ItemInList(int item_id, String itemName, int quantity) {
		this.item_id = item_id;
		this.quantity = quantity;
		this.itemName = itemName;
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
	 * Gets the price.
	 *
	 * @return the price
	 */
	public int getPrice() {
		return price;
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
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public int getItem_id() {
		return item_id;
	}

	/**
	 * Sets the item id.
	 *
	 * @param item_id the new item id
	 */
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	
	/**
	 * Sets the item type.
	 *
	 * @param item_type the new item type
	 */
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	
	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Sets the item name.
	 *
	 * @param itemName the new item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Gets the catalog type.
	 *
	 * @return the catalog type
	 */
	public String getCatalog_Type() {
		switch (catalogType) {
			case custom:return "Custom";
			case pre_define:return "Pre define";
			case new_item: return "New item";
		}
		return "";
	}

	/**
	 * Sets the catalog type.
	 *
	 * @param catalog_Type the new catalog type
	 */
	public void setCatalog_Type(String catalog_Type) {
		this.catalog_Type = catalog_Type;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ItemInList [item_id=" + item_id + ", quantity=" + quantity + ", price=" + price + ", itemName="
				+ itemName + ", item_type=" + item_type + ", catalog_Type=" + catalog_Type + "]";
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