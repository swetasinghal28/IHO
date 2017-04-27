package com.iho.asu.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Barathi on 7/4/2014.
 */
public class Gallery {
    private String id;
    private byte[] img;
    @SerializedName("title")   private String imageCaption;
    private String lectEmail;
    private Integer order;
    private String image;

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public void setLectEmail(String lectEmail) {
        this.lectEmail = lectEmail;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public String getLectEmail() {
        return lectEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg (byte[] img) {
        this.img = img;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        return sb.append(order.toString()).append(" ").append(imageCaption).toString();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
