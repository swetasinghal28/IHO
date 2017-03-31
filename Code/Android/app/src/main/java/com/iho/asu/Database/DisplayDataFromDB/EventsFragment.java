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
import com.google.gson.Gson;
import com.iho.asu.AppController;
import com.iho.asu.Comparators.EventsComparator;
import com.iho.asu.Containers.NewsContainer;
import com.iho.asu.Database.Columns;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.Events;
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

import static com.iho.asu.IHOConstants.EVENTS_URL;
import static com.iho.asu.IHOConstants.EVENT_DESC;
import static com.iho.asu.IHOConstants.EVENT_ID;
import static com.iho.asu.IHOConstants.EVENT_LOC_LINK;
import static com.iho.asu.IHOConstants.EVENT_REG;
import static com.iho.asu.IHOConstants.EVENT_TITLE;
import static com.iho.asu.IHOConstants.EVENT_WHEN;
import static com.iho.asu.IHOConstants.EVENT_WHERE;

public class EventsFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "Events";
    private static final String TAG = "Events";

    private SQLiteDatabase database;
    private ArrayList<String> eventsTitle = new ArrayList<String>();
    protected Map<String,Events> eventsItems = new HashMap<String, Events>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_events, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        eventsItems.clear();
        eventsTitle.clear();
        //getEventsItems();
        getEventsJson();
        //CustomList2 adapter = new
          //      CustomList2(this.getActivity(), eventsTitle);
        /*ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, eventsTitle);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        return v;
    }

    private void getEventsJson() {
        Log.i(TAG, "getEventsJson");

        JsonArrayRequest request = new JsonArrayRequest(EVENTS_URL.toString(),
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
            String id = null, when = null, where = null, desc = null, title = null, locLink = null, reg = null;


            List<Events> eventList = new ArrayList<Events>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject event = jsonArray.getJSONObject(i);

                if (!event.isNull(EVENT_ID)) {
                    id = event.getString(EVENT_ID);
                }

                if (!event.isNull(EVENT_TITLE)) {
                    title = event.getString(EVENT_TITLE);
                }

                if (!event.isNull(EVENT_DESC)) {
                    desc = event.getString(EVENT_DESC);
                }

                if (!event.isNull(EVENT_WHEN)) {
                    when = event.getString(EVENT_WHEN);
                }

                if (!event.isNull(EVENT_WHERE)) {
                    where = event.getString(EVENT_WHERE);
                }

                if (!event.isNull(EVENT_REG)) {
                    reg = event.getString(EVENT_REG);
                }

                if (!event.isNull(EVENT_LOC_LINK)) {
                    locLink = event.getString(EVENT_LOC_LINK);
                }


                Events e = new Events();
                e.setId(id);
                e.setTitle(title);
                e.setDescription(desc);
                e.setWhere(where);
                e.setDate(new Date(when));
                e.setWhen(when);
                e.setLocation_link(locLink);
                e.setReg(reg);


                Log.i(TAG, i + ": " + e.toString());

                eventList.add(e);
                eventsItems.put(title, e);
            }

            Collections.sort(eventList, new EventsComparator());
            for (Events event: eventList) {
                eventsTitle.add(event.getTitle());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, eventsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String name = eventsTitle.get(position);
        Events events = eventsItems.get(name);
        i.putExtra(Columns.KEY_EVENT_TITLE.getColumnName(), name);
        i.putExtra(Columns.KEY_EVENT_WHEN.getColumnName(),events.getWhen());
        i.putExtra(Columns.KEY_EVENT_MAP.getColumnName(),events.getLocation_link());
        i.putExtra(Columns.KEY_EVENT_WHERE.getColumnName(),events.getWhere());
        i.putExtra(Columns.KEY_EVENT_DESC.getColumnName(),events.getDescription());
        i.putExtra(Columns.KEY_EVENT_REG.getColumnName(),events.getReg());
        i.putExtra("ViewNeeded","Events");
        startActivity(i);
    }

    //Extracting elements from the database
    private void getEventsItems() {
        String[] columns = Columns.getEventColumnNames();
        Cursor eventsCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_EVENT_ID.getColumnName());
        eventsCursor.moveToFirst();
        while (!eventsCursor.isAfterLast()) {
            cursorToEvents(eventsCursor);
            eventsCursor.moveToNext();
        }
        String temp = "Friday, February 15, 2018, 5:30 PM";
        Log.i(TAG, new Date(temp).toString());
        String temp2 = "Tuesday, Apr 27, 2018, 4:00 PM";
        Log.i(TAG, new Date(temp2).toString());
        eventsCursor.close();
    }

    private void cursorToEvents(Cursor cursor) {
        Events n = new Events();
        String title = cursor.getString(4);
        //n.setId(cursor.getLong(0));
        n.setTitle(title);
        n.setWhen(cursor.getString(1));
        n.setLocation_link(cursor.getString(2));
        n.setWhere(cursor.getString(3));
        n.setDescription(cursor.getString(5));
        n.setReg(cursor.getString(6));

        //Log.i(TAG, n.toString() + " " + n.getLocation_link() + "    " + n.getReg());

        eventsTitle.add(title);
        eventsItems.put(title, n);
    }
}
