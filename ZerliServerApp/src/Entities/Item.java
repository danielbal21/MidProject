package Entities;


public class Item {
	private int id;
	private String name;
	private int price;
	private  CatalogType catalogType ;
	private ItemType itemType;
	private Color color;
	
	public Item( int id, String name,int price, CatalogType catalogType, ItemType itemType, Color color) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.catalogType = catalogType;
		this.itemType = itemType;
		this.color = color;
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
}