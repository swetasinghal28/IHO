package com.iho.asu.Database.DisplayDataFromDB;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.iho.asu.AppController;
import com.iho.asu.Database.Columns;
import com.iho.asu.Database.CustomList2;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.News;
import com.iho.asu.R;
//import com.sun.mail.iap.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NewsFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";
    private static final String URL = "http://107.170.239.62:3000/news";
    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "News";
    private static final String TAG = "News";
    private SQLiteDatabase database;
    private ArrayList<String> newsTitle = new ArrayList<String>();
    protected Map<String,News> newsItems = new HashMap<String, News>();
    private byte[] dummyImage;
    private String dummyLink;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_news, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        newsItems.clear();
        newsTitle.clear();
        //getNewsItems();
        getDummyImageAndLink();
        getNewsJson();

        //CustomList2 adapter = new
               // CustomList2(this.getActivity(), newsTitle);
        /*ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String name = newsTitle.get(position);
        News news = newsItems.get(name);
        i.putExtra(Columns.KEY_NEWS_TITLE.getColumnName(), name);
        i.putExtra(Columns.KEY_NEWS_IMAGE.getColumnName(),news.getImage());
        i.putExtra(Columns.KEY_NEWS_LINK.getColumnName(),news.getNewsLink());
        i.putExtra(Columns.KEY_NEWS_TEXT.getColumnName(),news.getText());
        i.putExtra("ViewNeeded","News");
        startActivity(i);
    }

    private void getNewsJson() {
        Log.i(TAG, "getNewsJson");
        JsonArrayRequest request = new JsonArrayRequest(URL.toString(),
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
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }
    private void parseJSONResult(JSONArray jsonArray) {
        try {
            //JSONArray jsonArray = result.getJSONArray("results");
            //ArrayAdapter myAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1);
                Log.i(TAG, "parseJSONResult");
                String title = null, newsDesc = null;
                long id = 0;
                //mMap.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject news = jsonArray.getJSONObject(i);

                    //id = place.getString(SUPERMARKET_ID);
                    //place_id = place.getString(PLACE_ID);
                    if (!news.isNull("title")) {
                        title = news.getString("title");
                    }

                    if (!news.isNull("desc")) {
                        newsDesc = news.getString("desc");
                    }

                    News n = new News();
                    n.setId(id++);
                    n.setTitle(title);
                    n.setText(newsDesc);
                    n.setImage(dummyImage);
                    n.setNewsLink(dummyLink);
                    Log.i(TAG, i + ": " + n.toString());
                    newsTitle.add(title);
                    newsItems.put(title, n);

                    ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
                    this.setListAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    
                    /*if (!place.isNull(VICINITY)) {
                        vicinity = place.getString(VICINITY);
                    }
                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LATITUDE);
                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LONGITUDE);
                    reference = place.getString(REFERENCE);*/


                    //myAdapter.add(placeName + "    Rating ( " + rate + " ) ");
                    //MarkerOptions markerOptions = new MarkerOptions();
                    //LatLng latLng = new LatLng(latitude, longitude);
                    //markerOptions.position(latLng);
                    //markerOptions.title(placeName + " : " + vicinity);

                    // mMap.addMarker(markerOptions);
                    //lstPlaces.setAdapter(myAdapter);
                }

               // Toast.makeText(getBaseContext(), jsonArray.length() + " Supermarkets found!",
                     //   Toast.LENGTH_LONG).show();


        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }
    private void getDummyImageAndLink() {
        Log.i(TAG, "getDummyImageAndLink");
        String[] columns = Columns.getNewsColumnNames();
        Cursor newsCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_NEWS_ID.getColumnName()+" DESC");
        newsCursor.moveToFirst();
        while (!newsCursor.isAfterLast()) {
            Log.i(TAG, "dummyimage_cursor_loop");
            dummyImage = newsCursor.getBlob(3);
            dummyLink = newsCursor.getString(4);
            //cursorToNews(newsCursor);
            newsCursor.moveToNext();
            break;
        }
        newsCursor.close();
    }
    //Extracting elements from the database
    private void getNewsItems() {
        String[] columns = Columns.getNewsColumnNames();
        Cursor newsCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_NEWS_ID.getColumnName()+" DESC");
        newsCursor.moveToFirst();
        while (!newsCursor.isAfterLast()) {
            cursorToNews(newsCursor);
            newsCursor.moveToNext();
        }
        newsCursor.close();
    }

    private void cursorToNews(Cursor cursor) {
        News n = new News();
        String title = cursor.getString(1);
        n.setId(cursor.getLong(0));
        n.setTitle(title);
        n.setText(cursor.getString(2));
        n.setImage(cursor.getBlob(3));
        n.setNewsLink(cursor.getString(4));
        newsTitle.add(title);
        newsItems.put(title, n);
    }

}
