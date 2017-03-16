package com.iho.asu.Database.DisplayDataFromDB;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.iho.asu.AppController;
import com.iho.asu.Comparators.NewsComparator;
import com.iho.asu.Containers.NewsContainer;
import com.iho.asu.Database.Columns;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.News;
import com.iho.asu.JSONResourceReader;
import com.iho.asu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iho.asu.IHOConstants.NEWS_DATE;
import static com.iho.asu.IHOConstants.NEWS_DESC;
import static com.iho.asu.IHOConstants.NEWS_ID;
import static com.iho.asu.IHOConstants.NEWS_IMAGE;
import static com.iho.asu.IHOConstants.NEWS_LINK;
import static com.iho.asu.IHOConstants.NEWS_TITLE;
import static com.iho.asu.IHOConstants.NEWS_URL;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NewsFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";
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

        JsonArrayRequest request = new JsonArrayRequest(NEWS_URL.toString(),
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
            newsTitle.add(news.getTitle());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {

                Log.i(TAG, "parseJSONResult");
                String id = null, title = null, newsDesc = null, newsLink = null, image = null, dateStr = null;


                List<News> newsList = new ArrayList<News>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject news = jsonArray.getJSONObject(i);

                    if (!news.isNull(NEWS_ID)) {
                        id = news.getString(NEWS_ID);
                    }

                    if (!news.isNull(NEWS_TITLE)) {
                        title = news.getString(NEWS_TITLE);
                    }

                    if (!news.isNull(NEWS_DESC)) {
                        newsDesc = news.getString(NEWS_DESC);
                    }

                    if (!news.isNull(NEWS_LINK)) {
                        newsLink = news.getString(NEWS_LINK);
                    }

                    if (!news.isNull(NEWS_IMAGE)) {
                        image = news.getString(NEWS_IMAGE);
                    }

                    if (!news.isNull(NEWS_DATE)) {
                        dateStr = news.getString(NEWS_DATE);
                    }


                    News n = new News();
                    n.setId(id);
                    n.setTitle(title);
                    n.setText(newsDesc);
                    n.setImage(Base64.decode(image, Base64.DEFAULT));
                    n.setNewsLink(newsLink);
                    n.setCreationDate(new Date(dateStr));

                    Log.i(TAG, i + ": " + n.toString());

                    newsList.add(n);
                    newsItems.put(title, n);
                }

                Collections.sort(newsList, Collections.<News>reverseOrder(new NewsComparator()));
                for (News news: newsList) {
                    newsTitle.add(news.getTitle());
                }

                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
                this.setListAdapter(adapter);
                adapter.notifyDataSetChanged();


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
        //n.setId(cursor.getLong(0));
        n.setTitle(title);
        n.setText(cursor.getString(2));
        n.setImage(cursor.getBlob(3));
        n.setNewsLink(cursor.getString(4));
        newsTitle.add(title);
        newsItems.put(title, n);
    }

}
