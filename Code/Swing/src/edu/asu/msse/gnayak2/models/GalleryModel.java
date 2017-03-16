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

	public GalleryModel(String title, String image) {
		id = UUID.randomUUID().toString().replace("-", "");
		this.title = title;
		this.image = image;
		
		
	}
	
	public GalleryModel(JSONObject object) {
		id = object.getString("id");
	    title = object.getString("title");	
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
