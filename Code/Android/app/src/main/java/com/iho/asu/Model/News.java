package com.iho.asu.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Barathi on 7/4/2014.
 */
public class News implements Cloneable {
    @Expose
    private String id;

    @Expose
    private String title;

    @Expose
    @SerializedName("desc")
    private String text;

    @Expose
    @SerializedName("link")
    private String newsLink;

    @Expose
    private String image;

    @Expose
    private String date;

    private byte[] img;
    private Date creationDate;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] image) {
        this.img = image;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        return sb.append(title).append(" ").append(text).append(" ").append(newsLink).toString();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
