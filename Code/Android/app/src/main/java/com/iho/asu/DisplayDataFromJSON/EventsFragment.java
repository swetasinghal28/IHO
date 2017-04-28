package com.iho.asu.DisplayDataFromJSON;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
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
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.iho.asu.Utilities.AppController;
import com.iho.asu.Comparators.EventsComparator;
import com.iho.asu.Constants.FragmentFieldsMapping;
import com.iho.asu.Model.Events;
import com.iho.asu.Utilities.JSONCache;
import com.iho.asu.Utilities.JSONResourceReader;
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

import static com.iho.asu.Constants.IHOConstants.EVENTS_IDS;
import static com.iho.asu.Constants.IHOConstants.EVENTS_URL;
import static com.iho.asu.Constants.IHOConstants.EVENT_DESC;
import static com.iho.asu.Constants.IHOConstants.EVENT_ID;
import static com.iho.asu.Constants.IHOConstants.EVENT_LOC_LINK;
import static com.iho.asu.Constants.IHOConstants.EVENT_REG;
import static com.iho.asu.Constants.IHOConstants.EVENT_TITLE;
import static com.iho.asu.Constants.IHOConstants.EVENT_WHEN;
import static com.iho.asu.Constants.IHOConstants.EVENT_WHERE;

public class EventsFragment extends ListFragment {


    private static final String TAG = "Events";

    private ArrayList<String> eventsTitle = new ArrayList<String>();
    protected HashMap<String,Events> eventsItems = new HashMap<String, Events>();
    private ArrayList<String> eventsIds = new ArrayList<String>();
    private boolean isContentChanged = false;
    private File path = null;
    private File file = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_events, container, false);

        Context context = v.getContext();
        path = context.getFilesDir();
        file = new File(path, "events.json");

        Log.i(TAG, "fetching Contents...");
        if (JSONCache.eventsItems.size() == 0) {
            //Cache Empty, Fetch  Events Objects
            Log.i(TAG,"Cache Empty, Fetch  Event Objects...");
            getEventsObjectJson();
        } else {
            //Cache not empty, checking if contents are modified
            Log.i(TAG,"Cache not empty, checking if contents are modified...");
            getEventIdsJSON();
        }

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        try {
            Intent i= new Intent(this.getActivity(),ViewActivity.class );
            String name = eventsTitle.get(position);
            Events events = eventsItems.get(name);
            i.putExtra(FragmentFieldsMapping.KEY_EVENT_TITLE.getColumnName(), name);
            i.putExtra(FragmentFieldsMapping.KEY_EVENT_WHEN.getColumnName(),events.getWhen());
            i.putExtra(FragmentFieldsMapping.KEY_EVENT_MAP.getColumnName(),events.getLocation_link());
            i.putExtra(FragmentFieldsMapping.KEY_EVENT_WHERE.getColumnName(),events.getWhere());
            i.putExtra(FragmentFieldsMapping.KEY_EVENT_DESC.getColumnName(),events.getDescription());
            i.putExtra(FragmentFieldsMapping.KEY_EVENT_REG.getColumnName(),events.getReg());
            i.putExtra("ViewNeeded","Events");
            startActivity(i);
        } catch (Exception e) {
            Log.e(TAG, "onListItemClick: Error=" + e.getMessage());
        }

    }

    private void getEventsObjectJson() {
        Log.i(TAG, "getEventsObjectJson");
        isContentChanged = false;
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
                        fetchJSONRaw();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {

            Files.write(jsonArray.toString().getBytes(), file);

            Log.i(TAG, "parseJSONResult");
            JSONCache.eventsItems.clear();
            JSONCache.eventsTitle.clear();
            JSONCache.eventsIds.clear();
            eventsItems.clear();
            eventsTitle.clear();
            eventsIds.clear();
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
                    where = event.getString(EVENT_WHERE).replace("\\n","\n");
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
                eventsIds.add(event.getId());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, eventsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.eventsItems = (HashMap<String, Events>) eventsItems.clone();
            JSONCache.eventsTitle = (ArrayList<String>) eventsTitle.clone();
            JSONCache.eventsIds = (ArrayList<String>) eventsIds.clone();


        } catch (JSONException e) {


            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (IOException e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    private void getEventIdsJSON() {
        Log.i(TAG, "getEventIdsJSON");

        JsonArrayRequest request = new JsonArrayRequest(EVENTS_IDS,
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

            List<String> eventIDs = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject events = jsonArray.getJSONObject(i);

                if (!events.isNull(EVENT_ID)) {
                    id = events.getString(EVENT_ID);
                }

                eventIDs.add(id);
            }

            ArrayList<String> oldListIds = (ArrayList<String>) JSONCache.eventsIds.clone();
            if (oldListIds.size() == 0) {
                Log.i(TAG, "Local IdList is empty");
                isContentChanged = true;
            } else if (oldListIds.size() != eventIDs.size()) {
                Log.i(TAG, "Local IdList Size: " + oldListIds.size());
                Log.i(TAG, "Server IdList Size: " + eventIDs.size());
                isContentChanged = true;
            } else {
                for (String i: eventIDs) {
                    if (!oldListIds.contains(i)) {
                        isContentChanged = true;
                        break;
                    }
                }
            }
            if (isContentChanged) {
                Log.i(TAG, "Content Changed on server, fetching from server...");
                getEventsObjectJson();
            } else {
                Log.i(TAG, "Content not Changed on server, fetching from local cache...");
                getJSONCache();
            }

        } catch (JSONException e) {

            isContentChanged = false;
            getJSONCache();

            Log.e(TAG, "parseJSONIDResult: Error=" + e.getMessage());

        } catch (Exception e) {

            isContentChanged = false;
            getJSONCache();

            Log.e(TAG, "parseJSONIDResult: Error=" + e.getMessage());
        }

    }

    public void getJSONCache() {
        try {
            Log.i(TAG, "getJSONCache");
            eventsItems.clear();
            eventsTitle.clear();

            eventsItems = (HashMap<String, Events>) JSONCache.eventsItems.clone();
            List<Events> eventList = new ArrayList<>(JSONCache.eventsItems.values());
            Collections.sort(eventList, new EventsComparator());
            for (Events event: eventList) {
                eventsTitle.add(event.getTitle());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, eventsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e(TAG, "getJSONCache: Error=" + e.getMessage());
        }

    }

    private void fetchJSONRaw(){
        try {

            JSONCache.eventsItems.clear();
            JSONCache.eventsTitle.clear();
            JSONCache.eventsIds.clear();
            eventsItems.clear();
            eventsTitle.clear();
            eventsIds.clear();
            String contents = null;

            if (file.length() == 0) {
                Log.i(TAG,"File storage empty, fetching from resources...");
                JSONResourceReader jsonResourceReader = new JSONResourceReader(getResources(), R.raw.events);
                contents = jsonResourceReader.jsonString;
            } else {
                Log.i(TAG, "fetching JSON from filestorage");
                contents = Files.toString(file, Charset.forName("UTF-8"));
            }

            Gson gson = new Gson();
            Events[] eventsArray = gson.fromJson(contents, Events[].class);
            List<Events> eventsList = new ArrayList<>();
            for (Events event: eventsArray) {
                event.setDate(new Date(event.getWhen()));
                event.setWhere(event.getWhere().replace("\\n","\n"));
                Log.i(TAG,event.toString());
                eventsList.add(event);
                eventsItems.put(event.getTitle(),event);
            }

            Collections.sort(eventsList, new EventsComparator());
            for (Events event: eventsList) {
                eventsTitle.add(event.getTitle());
                eventsIds.add(event.getId());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, eventsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.eventsItems = (HashMap<String, Events>) eventsItems.clone();
            JSONCache.eventsTitle = (ArrayList<String>) eventsTitle.clone();
            JSONCache.eventsIds = (ArrayList<String>) eventsIds.clone();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "fetchJSONRaw: Error= " + e.getMessage());
        }
    }

}
