package edu.nus.iss.SE24PT8.universityStore.util;
/**
*
* @author Mugunthan
*/
public class Item<V> implements Comparable<Item> {
	private V value;
	private String description;
	
	public Item(V value, String description) {
		this.value = value;
		this.description = description;
	}

	public V getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public int compareTo(Item item) {
		return getDescription().compareTo(item.getDescription());
	}


	@Override
	public boolean equals(Object object) {
		Item item = (Item) object;
		if (item != null)
			return value.equals(item.getValue());
		else
			return false;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return description;
	}
}