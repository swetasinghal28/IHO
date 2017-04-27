package com.iho.asu.Utilities;

import com.iho.asu.Model.Events;
import com.iho.asu.Model.Gallery;
import com.iho.asu.Model.Lecturer;
import com.iho.asu.Model.News;
import com.iho.asu.Model.Science;

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

    //featured news cache
    public static ArrayList<String> fNewsTitle = new ArrayList<String>();
    public static HashMap<String,News> fNewsItems = new HashMap<String, News>();
    public static ArrayList<String> fNewsIds = new ArrayList<String>();

    //events cache
    public static ArrayList<String> eventsTitle = new ArrayList<String>();
    public static HashMap<String,Events> eventsItems = new HashMap<String, Events>();
    public static ArrayList<String> eventsIds = new ArrayList<String>();

    //science cache

    public static ArrayList<String> scienceTitle = new ArrayList<String>();
    public static HashMap<String,Science> scienceItems = new HashMap<String, Science>();
    public static ArrayList<String> scienceIds = new ArrayList<String>();

    //lecturer cache
    public static ArrayList<String> lecturerNames = new ArrayList<String>();
    public static HashMap<String,Lecturer> lecturers = new HashMap<String, Lecturer>();
    public static ArrayList<String> lecturerIds = new ArrayList<String>();

    //gallery cache

    public static HashMap<String, Gallery> galleryMap = new HashMap<String, Gallery>();
    public static ArrayList<String> galleryIds = new ArrayList<String>();
}
