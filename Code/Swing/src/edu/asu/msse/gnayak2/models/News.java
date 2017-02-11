package edu.asu.msse.gnayak2.models;

import java.util.UUID;

public class News {
	String id;
	String title;
	String desc;
	String link;

	public News(String newsTitle, String description) {
		id = UUID.randomUUID().toString().replace("-", "");
		
		title = newsTitle;
		desc = description;
	}

	public News(String newsTitle, String description, String linkMore) {
		id = UUID.randomUUID().toString().replace("-", "");
		title = newsTitle;
		desc = description;
		link = linkMore;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
