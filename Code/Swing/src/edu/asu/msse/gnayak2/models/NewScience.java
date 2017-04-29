package edu.asu.msse.gnayak2.models;

import java.util.UUID;

import org.json.JSONObject;

public class NewScience {
	String id;
	String title;
	int order;
	String link;

	public NewScience(String newsTitle, int order, String linkMore) {
		id = UUID.randomUUID().toString().replace("-", "");
		title = newsTitle;
		this.order = order;
		link = linkMore;
	}
	
	public NewScience(JSONObject object) {
		id = object.getString("id");
		title = object.getString("title");
		order = object.getInt("order");
		link = object.getString("link");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String toString() {
		return title;
	}
}
