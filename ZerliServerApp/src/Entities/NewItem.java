package Entities;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class NewItem.
 */
public class NewItem extends ItemInList{
	
	/** The assemble. */
	/* assemble <item_id,quantity> */
	private ArrayList<ItemInList> assemble;
	
	/**
	 * Instantiates a new new item.
	 */
	public NewItem() {
		super();
		assemble = new ArrayList<>();
	}
	
	/**
	 * Adds the item.
	 *
	 * @param item the item
	 */
	public void addItem(ItemInList item) {
		if(assemble.size()==0) {
			assemble.add(item);
			return;
		}
		int  i=0;
		
		for(ItemInList items: assemble) {
			if(items.getItem_id() == item.getItem_id()) break;
			else i++;
		}
		
		if(i == assemble.size()) {
			assemble.add(item);
		}
		else {
			ItemInList chosen = assemble.get(i);
			assemble.remove(i);
			chosen.setQuantity(chosen.getQuantity()+item.getQuantity());
			assemble.add(chosen);
		}
		
	}
	
	/**
	 * Delete item.
	 *
	 * @param item the item
	 */
	public void deleteItem(ItemInList item) {
		if(assemble.size()==0) {
			return;
		}
		
		int i=0;
		
		for(ItemInList items: assemble) {
			if(items.getItem_id() == item.getItem_id()) break;
			else i++;
		}
		

		if(i == assemble.size()) {
			return;
		}
		else {
			assemble.remove(i);
		}
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		String str = "";
		for(ItemInList items:assemble) {
			str+= items.toString();
			str+= "\n";
		}
		return str;
	}
		
	/**
	 * Gets the assemble.
	 *
	 * @return the assemble
	 */
	public ArrayList<ItemInList> getAssemble() {
		return assemble;
	}

	/**
	 * Sets the assemble.
	 *
	 * @param assemble the new assemble
	 */
	public void setAssemble(ArrayList<ItemInList> assemble) {
		this.assemble = assemble;
	}

}
