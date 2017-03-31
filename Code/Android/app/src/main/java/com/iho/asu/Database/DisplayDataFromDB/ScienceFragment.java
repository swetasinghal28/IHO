package com.iho.asu.Database.DisplayDataFromDB;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.iho.asu.AppController;
import com.iho.asu.Database.Columns;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.Science;
import com.iho.asu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iho.asu.IHOConstants.SCIENCE_ID;
import static com.iho.asu.IHOConstants.SCIENCE_LINK;
import static com.iho.asu.IHOConstants.SCIENCE_TITLE;
import static com.iho.asu.IHOConstants.SCIENCE_URL;

public class ScienceFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "Science";
    private static final String TAG = "Science";

    private SQLiteDatabase database;
    int i = 0;
    private Map<String,String> scienceDetailedTitle = new HashMap<String, String>();
    private ArrayList<String> scienceTitle = new ArrayList<String>();
    protected Map<String,Science> scienceItems;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_science, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        scienceItems = new HashMap<String, Science>();
        scienceItems.clear();
        //getScienceItems();
        getScienceJson();
        //ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, scienceDetailedTitle.keySet().toArray(new String[0]));
        //CustomList2 adapter = new
                //CustomList2(this.getActivity(), new ArrayList(scienceDetailedTitle.keySet()));
        //this.setListAdapter(adapter);
        //adapter.notifyDataSetChanged();
        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String title = scienceTitle.get(position);
        Science science = scienceItems.get(title);
        i.putExtra(Columns.KEY_SCIENCE_TITLE.getColumnName(), title);
        i.putExtra(Columns.KEY_SCIENCE_LINK.getColumnName(),science.getLink());
        i.putExtra("ViewNeeded","Science");
        startActivity(i);
    }

    private void getScienceJson() {
        Log.i(TAG, "getScienceJson");

        JsonArrayRequest request = new JsonArrayRequest(SCIENCE_URL.toString(),
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
        /*JSONResourceReader resourceReader = new JSONResourceReader(getResources(), R.raw.newsobjects);
        String str = resourceReader.jsonString;

        Gson gson = new Gson();
        NewsContainer newsContainer = gson.fromJson(str, NewsContainer.class);

        ArrayList<News> newsList = newsContainer.getNewsList();

        for (News news: newsList) {
            //newsTitle.add(news.getTitle());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();*/
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {

            Log.i(TAG, "parseJSONResult");
            String id = null, title = null, link = null;


            List<Science> scienceList = new ArrayList<Science>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject sciences = jsonArray.getJSONObject(i);

                if (!sciences.isNull(SCIENCE_ID)) {
                    id = sciences.getString(SCIENCE_ID);
                }

                if (!sciences.isNull(SCIENCE_TITLE)) {
                    title = sciences.getString(SCIENCE_TITLE);
                }

                if (!sciences.isNull(SCIENCE_LINK)) {
                    link = sciences.getString(SCIENCE_LINK);
                }

                Science s = new Science();
                s.setId(id);
                s.setTitle(title);
                s.setLink(link);

                Log.i(TAG, i + ": " + s.toString());

                scienceTitle.add(s.getTitle());
                scienceItems.put(title, s);
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, scienceTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    //Extracting elements from the database
    private void getScienceItems() {
        String[] columns = Columns.getScienceColumnNames();
        Cursor scienceCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_SCIENCE_ID.getColumnName()+" DESC");
        scienceCursor.moveToFirst();
        while (!scienceCursor.isAfterLast()) {
            cursorToScience(scienceCursor);
            scienceCursor.moveToNext();
        }
        scienceCursor.close();
    }

    private void cursorToScience(Cursor cursor) {
        Science n = new Science();
        String title = cursor.getString(1);
        //n.setId(cursor.getLong(0));
        n.setTitle(title);
        n.setLink(cursor.getString(2));
        scienceItems.put(title, n);
        scienceDetailedTitle.put(title,title);
    }
}
