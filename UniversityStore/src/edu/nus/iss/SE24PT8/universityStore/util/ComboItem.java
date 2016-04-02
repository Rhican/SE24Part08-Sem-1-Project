package edu.nus.iss.SE24PT8.universityStore.util;

public class ComboItem {
	private Object value;
	private String label;
	
	public ComboItem(Object obj, String label){
		this.value = obj;
		this.label = label;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String toString(){
		return label;
	}
	
}
