package com.iho.asu.Database.Tables;

import java.util.Arrays;

/**
 * Created by Barathi on 7/4/2014.
 */
public class News {
    private long id;
    private String title;
    private String text;
    private byte[] image;
    private String newsLink;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        return sb.append(title).append(" ").append(Arrays.toString(image)).toString();
    }
}
