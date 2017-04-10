package com.iho.asu.Intermediate;

import android.util.Base64;

import com.iho.asu.Database.Tables.News;

import java.util.Date;

/**
 * Created by Mihir on 4/9/2017.
 */

public class IntermediateNews {
    private String id;
    private String title;
    private String text;
    private String image;
    private String newsLink;
    private String creationDate;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public News fromIntermediate() {
        News news = new News();
        news.setId(this.id);
        news.setNewsLink(this.newsLink);
        news.setText(this.text);
        news.setTitle(this.title);
        news.setImg(Base64.decode(this.image, Base64.DEFAULT));
        news.setCreationDate(new Date(this.creationDate));

        return news;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        return sb.append(title).append(" ").toString();
    }
}
