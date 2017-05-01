package com.iho.asu.DisplayDataFromJSON;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.iho.asu.Comparators.ScienceComparator;
import com.iho.asu.Model.Science;
import com.iho.asu.R;
import com.iho.asu.Utilities.AppController;
import com.iho.asu.Utilities.JSONCache;
import com.iho.asu.Utilities.JSONResourceReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.iho.asu.Constants.IHOConstants.SCIENCE_ID;
import static com.iho.asu.Constants.IHOConstants.SCIENCE_IDS;
import static com.iho.asu.Constants.IHOConstants.SCIENCE_LINK;
import static com.iho.asu.Constants.IHOConstants.SCIENCE_ORDER;
import static com.iho.asu.Constants.IHOConstants.SCIENCE_TITLE;
import static com.iho.asu.Constants.IHOConstants.SCIENCE_URL;

public class ScienceFragment extends ListFragment {

    private static final String TAG = "Science";

    int i = 0;

    private ArrayList<String> scienceTitle = new ArrayList<String>();
    protected HashMap<String, Science> scienceItems = new HashMap<String, Science>();
    private ArrayList<String> scienceIds = new ArrayList<String>();
    private boolean isContentChanged = false;
    private File path = null;
    private File file = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        try {
            v = inflater.inflate(
                    R.layout.fragment_science, container, false);


            Context context = v.getContext();
            path = context.getFilesDir();
            file = new File(path, "science.json");


            Log.i(TAG, "fetching Contents...");
            if (JSONCache.scienceItems.size() == 0) {
                //Cache Empty, Fetch  Events Objects
                Log.i(TAG, "Cache Empty, Fetch  Event Objects...");
                getScienceObjectJson();
            } else {
                //Cache not empty, checking if contents are modified
                Log.i(TAG, "Cache not empty, checking if contents are modified...");
                getScieceIdsJSON();
            }


            View layout = inflater.inflate(
                    R.layout.custom_toast, (ViewGroup) v.findViewById(R.id.toast_layout_root));


            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Click on item to read more!");

            Toast toast = new Toast(v.getContext());
            toast.setGravity((Gravity.AXIS_PULL_AFTER) << Gravity.AXIS_Y_SHIFT, 0, 100);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);

            for (int i = 1; i <= 2; i++) {
                toast.show();
            }

        } catch (Exception e) {
            Log.e(TAG, "onCreateView: Error=" + e.getMessage());
        }

        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try {
            String title = scienceTitle.get(position);
            Science science = scienceItems.get(title);
            Uri uri = Uri.parse(science.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "onListItemClick: Error=" + e.getMessage());
        }
    }

    private void getScienceObjectJson() {
        Log.i(TAG, "getScienceObjectJson");

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
                        fetchJSONRaw();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }


    private void parseJSONResult(JSONArray jsonArray) {
        try {

            Log.i(TAG, "parseJSONResult");
            String id = null, title = null, link = null, order = null;

            Log.i(TAG, "Writing json Array string to file");
            Files.write(jsonArray.toString().getBytes(), file);

            JSONCache.scienceItems.clear();
            JSONCache.scienceTitle.clear();
            JSONCache.scienceIds.clear();
            scienceTitle.clear();
            scienceIds.clear();
            scienceItems.clear();
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

                if (!sciences.isNull(SCIENCE_ORDER)) {
                    order = sciences.getString(SCIENCE_ORDER);
                }

                Science s = new Science();
                s.setId(id);
                s.setTitle(title);
                s.setLink(link);
                s.setOrder(Integer.parseInt(order));

                Log.i(TAG, i + ": " + s.toString());
                scienceList.add(s);
                scienceIds.add(s.getId());
                scienceItems.put(title, s);
            }
            Collections.sort(scienceList, new ScienceComparator());
            for (Science science: scienceList) {
                scienceTitle.add(science.getTitle());
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, scienceTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.scienceIds = (ArrayList<String>) scienceIds.clone();
            JSONCache.scienceItems = (HashMap<String, Science>) scienceItems.clone();
            JSONCache.scienceTitle = (ArrayList<String>) scienceTitle.clone();


        } catch (JSONException e) {
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (IOException e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    private void getScieceIdsJSON() {
        Log.i(TAG, "getScieceIdsJSON");

        JsonArrayRequest request = new JsonArrayRequest(SCIENCE_IDS,
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

            List<String> scienceIDs = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject sciences = jsonArray.getJSONObject(i);

                if (!sciences.isNull(SCIENCE_ID)) {
                    id = sciences.getString(SCIENCE_ID);
                }
                scienceIDs.add(id);
            }

            ArrayList<String> oldListIds = (ArrayList<String>) JSONCache.scienceIds.clone();
            if (oldListIds.size() == 0) {
                Log.i(TAG, "Local IdList is empty");
                isContentChanged = true;
            } else if (oldListIds.size() != scienceIDs.size()) {
                Log.i(TAG, "Local IdList Size: " + oldListIds.size());
                Log.i(TAG, "Server IdList Size: " + scienceIDs.size());
                isContentChanged = true;
            } else {
                for (String i : scienceIDs) {
                    if (!oldListIds.contains(i)) {
                        isContentChanged = true;
                        break;
                    }
                }
            }
            if (isContentChanged) {
                Log.i(TAG, "Content Changed on server, fetching from server...");
                getScienceObjectJson();
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
            scienceItems.clear();
            scienceTitle.clear();

            scienceTitle = (ArrayList<String>) JSONCache.scienceTitle.clone();
            scienceItems = (HashMap<String, Science>) JSONCache.scienceItems.clone();


            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, scienceTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e(TAG, "getJSONCache: Error=" + e.getMessage());
        }
    }

    private void fetchJSONRaw() {
        try {


            JSONCache.scienceIds.clear();
            JSONCache.scienceItems.clear();
            JSONCache.scienceTitle.clear();
            scienceItems.clear();
            scienceTitle.clear();
            scienceIds.clear();
            String contents = null;


            if (file.length() == 0) {
                Log.i(TAG, "File storage empty, fetching from resources...");
                JSONResourceReader jsonResourceReader = new JSONResourceReader(getResources(), R.raw.science);
                contents = jsonResourceReader.jsonString;
            } else {
                Log.i(TAG, "fetching JSON from filestorage");
                contents = Files.toString(file, Charset.forName("UTF-8"));
            }

            Gson gson = new Gson();
            Science[] scienceArray = gson.fromJson(contents, Science[].class);
            Arrays.sort(scienceArray, new ScienceComparator());
            for (Science science : scienceArray) {

                Log.i(TAG, science.toString());
                scienceIds.add(science.getId());
                scienceTitle.add(science.getTitle());
                scienceItems.put(science.getTitle(), science);
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, scienceTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.scienceItems = (HashMap<String, Science>) scienceItems.clone();
            JSONCache.scienceTitle = (ArrayList<String>) scienceTitle.clone();
            JSONCache.scienceIds = (ArrayList<String>) scienceIds.clone();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "fetchJSONRaw: Error= " + e.getMessage());
        }
    }
}
