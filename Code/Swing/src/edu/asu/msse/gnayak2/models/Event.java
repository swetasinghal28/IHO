package edu.asu.msse.gnayak2.models;

import java.util.UUID;

import org.json.JSONObject;

public class Event {
	String id;
	String title;
	String desc;
	String place;
	String location;
	String regURL;
	String date;

	public Event(String newsTitle, String description, String place, String location, String date, String regURL) {
		id = UUID.randomUUID().toString().replace("-", "");
		title = newsTitle;
		desc = description;
		this.place = place;
		this.location = location; 
		this.regURL = regURL;
		this.date = date;
		
	}
	
	public Event(JSONObject object) {
		id = object.getString("id");
		title = object.getString("title");
		desc = object.getString("desc");
		place = object.getString("place");
		location = object.getString("location");
		regURL = object.getString("regURL");
		date = object.getString("date");
		
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


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String getRegURL() {
		return regURL;
	}

	public void setRegURL(String regURL) {
		this.regURL = regURL;
	}
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String toString() {
		return title;
	}
}
