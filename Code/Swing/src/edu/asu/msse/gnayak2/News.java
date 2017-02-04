package edu.asu.msse.gnayak2;

public class News {
	String id;
	String desc;
	
	public News(String identifier, String description) {
		id = identifier;
		desc = description;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return id;
	}
}
