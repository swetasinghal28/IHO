package com.iho.asu.Database.Tables;

/**
 * Created by Barathi on 7/4/2014.
 */
public class Gallery {
    private long id;
    private byte[] name;
    private String imageCaption;
    private String lectEmail;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }
}
