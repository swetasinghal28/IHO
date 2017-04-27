package com.iho.asu.Model;

/**
 * Created by Barathi on 7/4/2014.
 */
public class Travel {
    private long id;
    private String text;
    private String link;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
