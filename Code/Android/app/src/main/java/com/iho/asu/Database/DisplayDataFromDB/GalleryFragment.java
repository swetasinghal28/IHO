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
import com.google.gson.Gson;
import com.iho.asu.AppController;
import com.iho.asu.Containers.NewsContainer;
import com.iho.asu.Database.CustomList;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.Gallery;
import com.iho.asu.Database.Tables.News;
import com.iho.asu.JSONResourceReader;
import com.iho.asu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.iho.asu.IHOConstants.GALLERY_URL;
import static com.iho.asu.IHOConstants.IMAGE;
import static com.iho.asu.IHOConstants.IMAGE_ID;
import static com.iho.asu.IHOConstants.IMAGE_TITLE;

public class GalleryFragment extends ListFragment {
    private static final String DB_NAME = "asuIHO.db";
    private static final String TABLE_NAME = "Gallery";
    private static final String TAG = "Gallery";
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
        galleryItems.clear();
        galleryTitle.clear();
        //getGalleryItems();
        getGalleryJson();
        /*CustomList adapter = new
                CustomList(this.getActivity(), galleryTitle, galleryItems);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        return v;
    }

    private void getGalleryJson() {
        Log.i(TAG, "getEventsJson");

        JsonArrayRequest request = new JsonArrayRequest(GALLERY_URL.toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray result) {

                        Log.i(TAG, "onResponse: Result = " + result.toString());
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

    private void fetchJSONRaw(){
        JSONResourceReader resourceReader = new JSONResourceReader(getResources(), R.raw.newsobjects);
        String str = resourceReader.jsonString;

        Gson gson = new Gson();
        NewsContainer newsContainer = gson.fromJson(str, NewsContainer.class);

        ArrayList<News> newsList = newsContainer.getNewsList();

        for (News news: newsList) {
            //newsTitle.add(news.getTitle());
        }

        //ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
        //this.setListAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {

            Log.i(TAG, "parseJSONResult");
            String id = null, image = null, title = null;


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

                Gallery img = new Gallery();
                img.setId(id);
                img.setImageCaption(title);
                img.setImage(Base64.decode(image, Base64.DEFAULT));


                Log.i(TAG, i + ": " + img.toString());

                gallery.add(img);
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