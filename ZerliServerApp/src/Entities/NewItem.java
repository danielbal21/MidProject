package Entities;

import java.util.ArrayList;

public class NewItem {
	
	private int price;
	ArrayList<ItemInList> assemble = new ArrayList<ItemInList>();
	
	public NewItem() {}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ArrayList<ItemInList> getAssemble() {
		return assemble;
	}

	public void setAssemble(ArrayList<ItemInList> assemble) {
		this.assemble = assemble;
	}
	
}