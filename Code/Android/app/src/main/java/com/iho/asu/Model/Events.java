package com.iho.asu.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Barathi on 7/4/2014.
 */
public class Events {
    @Expose
    private String id;

    @Expose
    @SerializedName("date")
    private String when;

    @Expose
    @SerializedName("place")
    private String where;

    @Expose
    @SerializedName("desc")
    private String description;

    @Expose
    private String title;

    @Expose
    @SerializedName("location")
    private String location_link;

    @Expose
    @SerializedName("regURL")
    private String reg;

    private Date eventDate;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        return sb.append(title).append(" ").toString();
    }

    public Date getDate() {
        return eventDate;
    }

    public void setDate(Date date) {
        this.eventDate = date;
    }
}
