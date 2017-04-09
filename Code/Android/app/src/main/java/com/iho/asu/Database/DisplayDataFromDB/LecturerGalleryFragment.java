package com.iho.asu.Database.DisplayDataFromDB;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.iho.asu.AppController;
import com.iho.asu.Comparators.ImageComparator;
import com.iho.asu.Database.CustomList;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.Gallery;
import com.iho.asu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.iho.asu.IHOConstants.*;

public class LecturerGalleryFragment extends ListFragment {
    private static final String DB_NAME = "asuIHO.db";
    private static final String TABLE_NAME = "Gallery";
    private static final String TAG = "Lecturer Gallery";
    private SQLiteDatabase database;
    private String LectKey = "";
    private ArrayList<String> galleryTitle = new ArrayList<String>();
    private ArrayList<byte[]> galleryItems = new ArrayList<byte[]>();
    private Integer[] imageId = new Integer[30];
    public LecturerGalleryFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        this.LectKey = bundle.getString(LECTURER_GALLERY_KEY);

        View v = inflater.inflate(
                R.layout.fragment_lecturer_gallery, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        //database = dbOpenHelper.openDataBase();
        //getGalleryItems();
        getGalleryItemsJson();
        /*CustomList adapter = new
                CustomList(this.getActivity(), galleryTitle, galleryItems);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        return v;
    }

    private void getGalleryItems() {
        Cursor galleryCursor = database.rawQuery("select * from "+TABLE_NAME+" where LectEmail = ?", new String[] { LectKey });
        galleryCursor.moveToFirst();
        while (!galleryCursor.isAfterLast()) {
            cursorToGallery(galleryCursor);
            galleryCursor.moveToNext();
        }
        galleryCursor.close();
    }

    private void getGalleryItemsJson() {
        Log.i(TAG, "getGalleryItemsJson");
        String URL = LECTURER_GALLERY_URL + LectKey;
        Log.i(TAG, URL);
        String tempURL = GALLERY_URL;
        JsonArrayRequest request = new JsonArrayRequest(tempURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray result) {

                        Log.i(TAG, "onResponse: Result = ");
                        parseJSONResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                        //fetchJSONRaw();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {

            Log.i(TAG, "parseJSONResult");
            String id = null, image = null, title = null, order = null;


            List<Gallery> gallery = new ArrayList<Gallery>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                if (!obj.isNull(IMAGE_ID)) {
                    id = obj.getString(IMAGE_ID);
                }

                if (!obj.isNull(IMAGE_TITLE)) {
                    title = obj.getString(IMAGE_TITLE);
                }

                if (!obj.isNull(IMAGE)) {
                    image = obj.getString(IMAGE);
                }

                if (!obj.isNull(IMAGE_ORDER)) {
                    order = obj.getString(IMAGE_ORDER);
                }

                Gallery img = new Gallery();
                img.setId(id);
                img.setImageCaption(title);
                img.setImage(Base64.decode(image, Base64.DEFAULT));
                img.setOrder(Integer.parseInt(order));

                //Log.i(TAG, i + ": " + img.toString());

                gallery.add(img);

            }

            Collections.sort(gallery, new ImageComparator());
            for(Gallery img: gallery) {
                Log.i(TAG, img.toString());
                galleryItems.add(img.getImage());
                galleryTitle.add(img.getImageCaption());
            }

            CustomList adapter = new
                    CustomList(this.getActivity(), galleryTitle, galleryItems);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    private void cursorToGallery(Cursor cursor) {
        if(!cursor.isNull( 2 ))galleryTitle.add(cursor.getString(2));
        if(!cursor.isNull( 1 ))galleryItems.add(cursor.getBlob(1));
    }

}