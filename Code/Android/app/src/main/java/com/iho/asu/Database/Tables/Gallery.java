package com.iho.asu.Database.Tables;

/**
 * Created by Barathi on 7/4/2014.
 */
public class Gallery {
    private String id;
    private byte[] image;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        return sb.append(imageCaption).append(" ").toString();
    }
}
