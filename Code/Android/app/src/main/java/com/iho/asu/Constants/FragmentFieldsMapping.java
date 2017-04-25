package com.iho.asu.Constants;

/**
 * Created by Barathi on 7/4/2014.
 * Modified by Mihir Bhatt 04/25/2017.
 */

public enum FragmentFieldsMapping {

    KEY_EVENT_WHERE("EventWhere"),
    KEY_EVENT_WHEN("EventWhen"),
    KEY_EVENT_TITLE("EventTitle"),
    KEY_EVENT_MAP("mapLink"),
    KEY_EVENT_REG("EventReg"),
    KEY_EVENT_DESC("EventDesc"),
    KEY_LECTURER_IMAGE("Image"),
    KEY_LECTURER_NAME("Name"),
    KEY_LECTURER_BIO("Bio"),
    KEY_LECTURER_LINK("Link"),
    KEY_LECTURER_EMAIL("Email"),
    KEY_LECTURE_TITLE("Title"),
    KEY_NEWS_TITLE("NewsTitle"),
    KEY_NEWS_IMAGE("NewsImage"),
    KEY_NEWS_TEXT("NewsText"),
    KEY_NEWS_LINK("NewsLink"),
    KEY_SCIENCE_LINK("ScienceLink"),
    KEY_SCIENCE_TITLE("ScienceTitle"),
    KEY_TRAVEL_LINK("TravelLink"),
    KEY_TRAVEL_TEXT("TravelText");

    private String name;

    private FragmentFieldsMapping(String name) {
        this.name = name;
    }

    public String getColumnName() {
        return name;
    }
}
