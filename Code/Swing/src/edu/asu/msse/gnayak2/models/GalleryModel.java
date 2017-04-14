package edu.asu.msse.gnayak2.models;

import java.util.UUID;

import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GalleryModel {
	String id;
	String title;
	String image;
    int order;
	public GalleryModel(String title, String image, int order) {
		id = UUID.randomUUID().toString().replace("-", "");
		this.title = title;
		this.image = image;
		this.order = order;
		
		
	}
	
	public GalleryModel(JSONObject object) {
		id = object.getString("id");
	    title = object.getString("title");	
       image = object.getString("image");
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getImage()
	{
		return image;
	}
	
	public void setImage(String image)
	{
		this.image = image;
	}

	
	public String toString() {
		return title;
	}
}
