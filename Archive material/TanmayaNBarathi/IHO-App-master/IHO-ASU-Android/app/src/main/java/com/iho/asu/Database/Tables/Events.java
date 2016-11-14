package com.iho.asu.Database.Tables;

/**
 * Created by Barathi on 7/4/2014.
 */
public class Events {
    private long id;
    private String when;
    private String where;
    private String description;
    private String title;
    private String location_link;
    private String reg;

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation_link() {
        return location_link;
    }

    public void setLocation_link(String location_link) {
        this.location_link = location_link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
