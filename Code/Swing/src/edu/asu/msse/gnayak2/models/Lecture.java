package edu.asu.msse.gnayak2.models;

import java.util.UUID;

import org.json.JSONObject;

public class Lecture {
	String id;
	String name;
	String bio;
	String link;
	String title;
	String email;
	String image;
	int order;

	public Lecture(String newsTitle, String bio, String linkMore,String title, String image, String email, int order) {
		id = UUID.randomUUID().toString().replace("-", "");
		name = newsTitle;
		this.bio = bio;
		link = linkMore;
		this.title = title;
		this.image = image;
		this.email = email;
		this.order = order;
	}
	
	public Lecture(JSONObject object) {
		id = object.getString("id");
		name = object.getString("name");
		bio = object.getString("bio");
		link = object.getString("link");
		title = object.getString("title");
		image = object.getString("image");
		email = object.getString("email");
		order = object.getInt("order");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage()
	{
		return image;
	}
	
	public void setImage(String image)
	{
		this.image = image;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String toString() {
		return name;
	}
}
