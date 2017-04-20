package edu.asu.msse.gnayak2.models;

import java.util.UUID;

import org.json.JSONObject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class News {
	String id;
	String title;
	String desc;
	String link;
	String date;
	String image;


	public News(String newsTitle, String description, String linkMore, String date, String image) {
		id = UUID.randomUUID().toString().replace("-", "");
		title = newsTitle;
		desc = description;
		link = linkMore;
		this.date = date;
		this.image = image;
		
		
	}
	
	public News(JSONObject object) {
		id = object.getString("id");
		title = object.getString("title");
		desc = object.getString("desc");
		link = object.getString("link");
      date = object.getString("date");
       image = object.getString("image");
      
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
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}


	public String getImage()
	{
		return image;
	}
	
	public void setImage(String image)
	{
		this.image = image;
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
