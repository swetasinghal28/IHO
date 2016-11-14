package com.iho.asu.Database.Tables;

/**
 * Created by Barathi on 7/4/2014.
 */
public class Science {
    private long id;
    private String title;
    private String link;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
