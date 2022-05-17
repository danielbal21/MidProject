package Entities;

import java.util.ArrayList;

public class NewItem extends ItemInList{
	
	/* assemble <item_id,quantity> */
	private ArrayList<ItemInList> assemble;
	
	public NewItem() {
		super();
		assemble = new ArrayList<>();
	}
	
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
	
	public ArrayList<ItemInList> getAssemble() {
		return assemble;
	}

	@Override
	public String toString() {
		String str = "";
		for(ItemInList items:assemble) {
			str+= items.toString();
			str+= "\n";
		}
		return str;
	}
		
}