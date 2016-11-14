package com.iho.asu.Database.DisplayDataFromDB;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iho.asu.Database.CustomList;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.R;

import java.util.ArrayList;

public class GalleryFragment extends ListFragment {
    private static final String DB_NAME = "asuIHO.db";
    private static final String TABLE_NAME = "Gallery";
    private SQLiteDatabase database;
    private ArrayList<String> galleryTitle = new ArrayList<String>();
    private ArrayList<byte[]> galleryItems = new ArrayList<byte[]>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.gallery, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        getGalleryItems();
        CustomList adapter = new
                CustomList(this.getActivity(), galleryTitle, galleryItems);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }

    private void getGalleryItems() {
        Cursor galleryCursor = database.rawQuery("select * from "+TABLE_NAME+" where LectEmail is null", null);
        galleryCursor.moveToFirst();
        while (!galleryCursor.isAfterLast()) {
            cursorToGallery(galleryCursor);
            galleryCursor.moveToNext();
        }
        galleryCursor.close();
    }

    private void cursorToGallery(Cursor cursor) {
        if(!cursor.isNull( 2 ))galleryTitle.add(cursor.getString(2));
        if(!cursor.isNull( 1 ))galleryItems.add(cursor.getBlob(1));
    }

}