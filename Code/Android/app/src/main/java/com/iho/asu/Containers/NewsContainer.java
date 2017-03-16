package com.iho.asu.Containers;

import com.google.gson.annotations.Expose;
import com.iho.asu.Database.Tables.News;

import java.util.ArrayList;

/**
 * Created by Mihir on 3/16/2017.
 */

public class NewsContainer {
    @Expose
    private ArrayList<News> newsList;


    public ArrayList<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.newsList = newsList;
    }
}
