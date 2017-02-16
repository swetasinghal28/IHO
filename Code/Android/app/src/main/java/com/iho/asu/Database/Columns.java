package com.iho.asu.Database;

/**
 * Created by Barathi on 7/4/2014.
 */
public enum Columns {
    KEY_GALLERY_CAPTION("ImageCaption"),KEY_GALLERY_LECT_EMAIL("LectEmail"),KEY_GALLERY_ID("ImageID"), KEY_GALLERY_NAME("ImageName"), KEY_EVENT_ID("EventId"), KEY_EVENT_WHERE("EventWhere"), KEY_EVENT_WHEN("EventWhen"), KEY_EVENT_TITLE("EventTitle"), KEY_EVENT_MAP("mapLink"), KEY_EVENT_REG("EventReg"), KEY_EVENT_DESC("EventDesc"), KEY_LECTURER_ID("LectID"), KEY_LECTURER_IMAGE("Image"), KEY_LECTURER_NAME("Name"), KEY_LECTURER_BIO("Bio"), KEY_LECTURER_LINK("Link"), KEY_LECTURER_EMAIL("Email"), KEY_LECTURE_TITLE("Title"), KEY_LECTURE_IMAGE("Image"), KEY_NEWS_ID("NewsID"), KEY_NEWS_TITLE("NewsTitle"), KEY_NEWS_IMAGE("NewsImage"), KEY_NEWS_TEXT("NewsText"), KEY_NEWS_LINK("NewsLink"), KEY_SCIENCE_ID("ScienceID"), KEY_SCIENCE_LINK("ScienceLink"), KEY_SCIENCE_TITLE("ScienceTitle"),KEY_TRAVEL_ID("TravelId"),KEY_TRAVEL_LINK("TravelLink"),KEY_TRAVEL_TEXT("TravelText");
    private String name;

    private Columns(String name) {
        this.name = name;
    }

    public String getColumnName() {
        return name;
    }

    public static String[] getTravelColumnNames() {
        return new String[]{KEY_TRAVEL_ID.name, KEY_TRAVEL_TEXT.name, KEY_TRAVEL_LINK.name};
    }

    public static String[] getEventColumnNames() {
        return new String[]{KEY_EVENT_ID.name, KEY_EVENT_WHEN.name, KEY_EVENT_MAP.name, KEY_EVENT_WHERE.name, KEY_EVENT_TITLE.name, KEY_EVENT_DESC.name, KEY_EVENT_REG.name};
    }

    public static String[] getGalleryColumnNames() {
        return new String[]{KEY_GALLERY_ID.name, KEY_GALLERY_NAME.name,KEY_GALLERY_CAPTION.name,KEY_GALLERY_LECT_EMAIL.name};
    }

    public static String[] getLecturerColumnNames() {
        return new String[]{KEY_LECTURER_ID.name, KEY_LECTURER_NAME.name, KEY_LECTURE_IMAGE.name, KEY_LECTURER_BIO.name, KEY_LECTURER_EMAIL.name, KEY_LECTURER_LINK.name, KEY_LECTURE_TITLE.name};
    }

    public static String[] getNewsColumnNames() {
        return new String[]{KEY_NEWS_ID.name, KEY_NEWS_TITLE.name, KEY_NEWS_TEXT.name, KEY_NEWS_IMAGE.name, KEY_NEWS_LINK.name};
    }

    public static String[] getScienceColumnNames() {
        return new String[]{KEY_SCIENCE_ID.name, KEY_SCIENCE_TITLE.name, KEY_SCIENCE_LINK.name};
    }
}
