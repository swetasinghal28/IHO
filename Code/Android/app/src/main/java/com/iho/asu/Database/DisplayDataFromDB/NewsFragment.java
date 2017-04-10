package com.iho.asu.Database.DisplayDataFromDB;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
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
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.iho.asu.AppController;
import com.iho.asu.Comparators.NewsComparator;
import com.iho.asu.Database.Columns;
import com.iho.asu.Database.Tables.News;
import com.iho.asu.JSONCache;
import com.iho.asu.JSONResourceReader;
import com.iho.asu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.iho.asu.IHOConstants.NEWS_DATE;
import static com.iho.asu.IHOConstants.NEWS_DESC;
import static com.iho.asu.IHOConstants.NEWS_ID;
import static com.iho.asu.IHOConstants.NEWS_IDS;
import static com.iho.asu.IHOConstants.NEWS_IMAGE;
import static com.iho.asu.IHOConstants.NEWS_LINK;
import static com.iho.asu.IHOConstants.NEWS_TITLE;
import static com.iho.asu.IHOConstants.NEWS_URL;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NewsFragment extends ListFragment {

    private static final String TAG = "News";

    private boolean isContentChanged = false;
    private ArrayList<String> newsTitle = new ArrayList<String>();
    protected HashMap<String,News> newsItems = new HashMap<String, News>();

    private File path = null;
    private File file = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(
                R.layout.fragment_news, container, false);

        Context context = v.getContext();
        path = context.getFilesDir();
        file = new File(path, "news.json");
        try {
            Files.write("".getBytes(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "fetching NewsContents...");
        if (JSONCache.newsItems.size() == 0) {
            //Cache Empty, Fetch  News Objects
            Log.i(TAG,"Cache Empty, Fetch  News Objects...");
            getNewsObjectJson();
        } else {
            //Cache not empty, checking if contents are modified
            Log.i(TAG,"Cache not empty, checking if contents are modified...");
            getNewsIdsJSON();
        }

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String name = newsTitle.get(position);
        News news = newsItems.get(name);
        i.putExtra(Columns.KEY_NEWS_TITLE.getColumnName(), name);
        i.putExtra(Columns.KEY_NEWS_IMAGE.getColumnName(),news.getImg());
        i.putExtra(Columns.KEY_NEWS_LINK.getColumnName(),news.getNewsLink());
        i.putExtra(Columns.KEY_NEWS_TEXT.getColumnName(),news.getText());
        i.putExtra("ViewNeeded","News");
        startActivity(i);
    }

    private void getNewsObjectJson() {
        Log.i(TAG, "getNewsObjectJson");
        isContentChanged = false;
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
                        fetchJSONRaw();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {
            Files.write(jsonArray.toString().getBytes(), file);

            Log.i(TAG, "parseJSONResult");
            JSONCache.newsItems.clear();
            JSONCache.newsTitle.clear();
            newsItems.clear();
            newsTitle.clear();
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
                n.setImg(Base64.decode(image, Base64.DEFAULT));
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

            Log.i(TAG, "Updating local cache...");
            JSONCache.newsItems = (HashMap<String, News>) newsItems.clone();
            JSONCache.newsTitle = (ArrayList<String>) newsTitle.clone();

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    private void getNewsIdsJSON() {
        Log.i(TAG, "getNewsIdsJSON");

        JsonArrayRequest request = new JsonArrayRequest(NEWS_IDS.toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray result) {

                        Log.i(TAG, "onResponse: Result = " + result.toString());
                        parseJSONIDResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                        getJSONCache();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJSONIDResult(JSONArray jsonArray) {
        try {

            Log.i(TAG, "parseJSONIDResult");

            String id = null;

            List<String> newsIDs = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject news = jsonArray.getJSONObject(i);

                if (!news.isNull(NEWS_ID)) {
                    id = news.getString(NEWS_ID);
                }

                newsIDs.add(id);
            }

            Set<String> oldListIds = JSONCache.newsItems.keySet();
            if (oldListIds.size() == 0) {
                Log.i(TAG, "oldListIds.size() :" + oldListIds.size());
                isContentChanged = true;
            } else if (oldListIds.size() != newsIDs.size()) {
                Log.i(TAG, "oldListIds.size() :" + oldListIds.size());
                Log.i(TAG, "newsIDs.size() :" + newsIDs.size());
                isContentChanged = true;
            } else {
                for (String i: newsIDs) {
                    if (!oldListIds.contains(i)) {
                        isContentChanged = true;
                        break;
                    }
                }
            }
            if (isContentChanged) {
                Log.i(TAG, "Content Changed on server, fetching from server...");
                getNewsObjectJson();
            } else {
                Log.i(TAG, "Content not Changed on server, fetching from local cache...");
                getJSONCache();
            }

        } catch (JSONException e) {

            isContentChanged = false;
            getJSONCache();
            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());

        } catch (Exception e) {

            isContentChanged = false;
            getJSONCache();
            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }

    }

    public void getJSONCache() {
        try {
            Log.i(TAG, "getJSONCache");
            newsItems.clear();
            newsTitle.clear();

            newsItems = (HashMap<String, News>) JSONCache.newsItems.clone();
            List<News> newsList = new ArrayList<>(JSONCache.newsItems.values());
            Collections.sort(newsList, Collections.<News>reverseOrder(new NewsComparator()));
            for (News news: newsList) {
                newsTitle.add(news.getTitle());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }

    }

    private void fetchJSONRaw(){
        try {
            Log.i(TAG, "fetching JSON from filestorage");

            JSONCache.newsItems.clear();
            JSONCache.newsTitle.clear();
            newsItems.clear();
            newsTitle.clear();
            String contents = null;

            contents = Files.toString(file, Charset.forName("UTF-8"));
            if ("".equals(contents)) {
                Log.i(TAG,"File storage empty, fetching from resources...");
                JSONResourceReader jsonResourceReader = new JSONResourceReader(getResources(), R.raw.newsobjects);
                contents = jsonResourceReader.jsonString;
            }

            Gson gson = new Gson();
            News[] newsArray = gson.fromJson(contents, News[].class);
            List<News> newsList = new ArrayList<>();
            for (News news: newsArray) {
                news.setImg(Base64.decode(news.getImage(), Base64.DEFAULT));
                news.setCreationDate(new Date(news.getDate()));
                Log.i(TAG,news.toString());
                newsList.add(news);
                newsItems.put(news.getTitle(),news);
            }

            Collections.sort(newsList, Collections.<News>reverseOrder(new NewsComparator()));
            for (News news: newsList) {
                newsTitle.add(news.getTitle());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.newsItems = (HashMap<String, News>) newsItems.clone();
            JSONCache.newsTitle = (ArrayList<String>) newsTitle.clone();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /*private void getDummyImageAndLink() {
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
    }*/

}
