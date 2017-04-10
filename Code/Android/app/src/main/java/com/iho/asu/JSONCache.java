package com.iho.asu;

import com.iho.asu.Database.Tables.Events;
import com.iho.asu.Database.Tables.News;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mihir on 4/9/2017.
 */

public class JSONCache {
    //news cache
    public static ArrayList<String> newsTitle = new ArrayList<String>();
    public static HashMap<String,News> newsItems = new HashMap<String, News>();
    public static ArrayList<String> newsIds = new ArrayList<String>();

    //events cache
    public static ArrayList<String> eventsTitle = new ArrayList<String>();
    public static HashMap<String,Events> eventsItems = new HashMap<String, Events>();
    public static ArrayList<String> eventsIds = new ArrayList<String>();
}
