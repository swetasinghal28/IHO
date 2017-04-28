package com.iho.asu.DisplayDataFromJSON;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
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
import com.iho.asu.Utilities.AppController;
import com.iho.asu.Comparators.LecturerComparator;
import com.iho.asu.Constants.FragmentFieldsMapping;
import com.iho.asu.Model.Lecturer;
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
import java.util.HashMap;
import java.util.List;

import static com.iho.asu.Constants.IHOConstants.LECTURER_BIO;
import static com.iho.asu.Constants.IHOConstants.LECTURER_EMAIL;
import static com.iho.asu.Constants.IHOConstants.LECTURER_ID;
import static com.iho.asu.Constants.IHOConstants.LECTURER_IDS;
import static com.iho.asu.Constants.IHOConstants.LECTURER_IMAGE;
import static com.iho.asu.Constants.IHOConstants.LECTURER_LINK;
import static com.iho.asu.Constants.IHOConstants.LECTURER_NAME;
import static com.iho.asu.Constants.IHOConstants.LECTURER_ORDER;
import static com.iho.asu.Constants.IHOConstants.LECTURER_TITLE;
import static com.iho.asu.Constants.IHOConstants.LECTURER_URL;


public class LecturerFragment extends ListFragment {


    private static final String TAG = "Lecturer";

    private ArrayList<String> lecturerNames = new ArrayList<String>();
    protected HashMap<String,Lecturer> lecturers = new HashMap<String, Lecturer>();
    private ArrayList<String> lecturerIds = new ArrayList<String>();
    private boolean isContentChanged = false;
    private File path = null;
    private File file = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(
                R.layout.fragment_lecturer, container, false);

        Context context = v.getContext();
        path = context.getFilesDir();
        file = new File(path, "lecturer.json");

        Log.i(TAG, "fetching Contents...");
        if (JSONCache.lecturers.size() == 0) {
            //Cache Empty, Fetch  News Objects
            Log.i(TAG,"Cache Empty, Fetch  Lecturer Objects...");
            getLecturesJson();
        } else {
            //Cache not empty, checking if contents are modified
            Log.i(TAG,"Cache not empty, checking if contents are modified...");
            getLecturerIdsJSON();
        }

        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        try {
            Intent i= new Intent(this.getActivity(),ViewActivity.class );
            String name = lecturerNames.get(position);
            Lecturer lecturer = lecturers.get(name);
            i.putExtra(FragmentFieldsMapping.KEY_LECTURER_NAME.getColumnName(), name);
            i.putExtra(FragmentFieldsMapping.KEY_LECTURER_BIO.getColumnName(),lecturer.getBio());
            i.putExtra(FragmentFieldsMapping.KEY_LECTURER_IMAGE.getColumnName(),lecturer.getImg());
            i.putExtra(FragmentFieldsMapping.KEY_LECTURER_EMAIL.getColumnName(),lecturer.getEmail());
            i.putExtra(FragmentFieldsMapping.KEY_LECTURE_TITLE.getColumnName(),lecturer.getTitle());
            i.putExtra(FragmentFieldsMapping.KEY_LECTURER_LINK.getColumnName(),lecturer.getLink());
            i.putExtra("ViewNeeded","Lecturer");
            startActivity(i);
        } catch (Exception e) {
            Log.e(TAG, "onListItemClick: Error=" + e.getMessage());
        }
    }

    private void getLecturesJson() {
        Log.i(TAG, "getLecturesJson");
        isContentChanged = false;
        JsonArrayRequest request = new JsonArrayRequest(LECTURER_URL,
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
            Log.i(TAG, "writing JSONArray to file storage");
            Files.write(jsonArray.toString().getBytes(), file);

            Log.i(TAG, "parseJSONResult");
            String id = null, title = null, name = null, bio = null, link = null, image = null, email = null, order = null;
            JSONCache.lecturers.clear();
            JSONCache.lecturerIds.clear();
            JSONCache.lecturerNames.clear();
            lecturers.clear();
            lecturerIds.clear();
            lecturerNames.clear();

            List<Lecturer> lectList = new ArrayList<Lecturer>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject lecturer = jsonArray.getJSONObject(i);

                if (!lecturer.isNull(LECTURER_ID)) {
                    id = lecturer.getString(LECTURER_ID);
                }

                if (!lecturer.isNull(LECTURER_TITLE)) {
                    title = lecturer.getString(LECTURER_TITLE).replace("\\n","\n");
                }

                if (!lecturer.isNull(LECTURER_NAME)) {
                    name = lecturer.getString(LECTURER_NAME);
                }

                if (!lecturer.isNull(LECTURER_LINK)) {
                    link = lecturer.getString(LECTURER_LINK);
                }

                if (!lecturer.isNull(LECTURER_IMAGE)) {
                    image = lecturer.getString(LECTURER_IMAGE);
                }

                if (!lecturer.isNull(LECTURER_EMAIL)) {
                    email = lecturer.getString(LECTURER_EMAIL);
                }

                if (!lecturer.isNull(LECTURER_BIO)) {
                    bio = lecturer.getString(LECTURER_BIO);
                }

                if (!lecturer.isNull(LECTURER_ORDER)) {
                    order = lecturer.getString(LECTURER_ORDER);
                }


                Lecturer l = new Lecturer();
                l.setId(id);
                l.setTitle(title);
                l.setBio(bio);
                l.setImg(Base64.decode(image, Base64.DEFAULT));
                l.setLink(link);
                l.setEmail(email);
                l.setName(name);
                l.setOrder(Integer.parseInt(order));

                Log.i(TAG, l.toString());

                lectList.add(l);
                lecturers.put(name,l);
            }

            Collections.sort(lectList, new LecturerComparator());
            for (Lecturer lect: lectList) {
                lecturerNames.add(lect.getName());
                lecturerIds.add(lect.getId());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lecturerNames);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.lecturers = (HashMap<String, Lecturer>) lecturers.clone();
            JSONCache.lecturerIds = (ArrayList<String>) lecturerIds.clone();
            JSONCache.lecturerNames = (ArrayList<String>) lecturerNames.clone();



        } catch (JSONException e) {


            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (IOException e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }
    private void getLecturerIdsJSON() {
        Log.i(TAG, "getLecturerIdsJSON");

        JsonArrayRequest request = new JsonArrayRequest(LECTURER_IDS,
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

            List<String> lecturerIDs = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ids = jsonArray.getJSONObject(i);

                if (!ids.isNull(LECTURER_ID)) {
                    id = ids.getString(LECTURER_ID);
                }

                lecturerIDs.add(id);
            }

            ArrayList<String> oldListIds = (ArrayList<String>) JSONCache.lecturerIds.clone();
            if (oldListIds.size() == 0) {
                Log.i(TAG, "Local IdList is empty");
                isContentChanged = true;
            } else if (oldListIds.size() != lecturerIDs.size()) {
                Log.i(TAG, "Local IdList Size: " + oldListIds.size());
                Log.i(TAG, "Server IdList Size: " + lecturerIDs.size());
                isContentChanged = true;
            } else {
                for (String i: lecturerIDs) {
                    if (!oldListIds.contains(i)) {
                        isContentChanged = true;
                        break;
                    }
                }
            }
            if (isContentChanged) {
                Log.i(TAG, "Content Changed on server, fetching from server...");
                getLecturesJson();
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
            lecturers.clear();
            lecturerNames.clear();

            lecturers = (HashMap<String, Lecturer>) JSONCache.lecturers.clone();
            List<Lecturer> lecturerList = new ArrayList<>(JSONCache.lecturers.values());
            Collections.sort(lecturerList, new LecturerComparator());
            for (Lecturer lecturer: lecturerList) {
                lecturerNames.add(lecturer.getName());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lecturerNames);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "getJSONCache: Error=" + e.getMessage());
        }

    }

    private void fetchJSONRaw(){
        try {

            JSONCache.lecturers.clear();
            JSONCache.lecturerNames.clear();
            JSONCache.lecturerIds.clear();
            lecturers.clear();
            lecturerNames.clear();
            lecturerIds.clear();
            String contents = null;

            if (file.length() == 0) {
                Log.i(TAG,"File storage empty, fetching from resources...");
                JSONResourceReader jsonResourceReader = new JSONResourceReader(getResources(), R.raw.lecturer);
                contents = jsonResourceReader.jsonString;
            } else {
                Log.i(TAG, "fetching JSON from filestorage");
                contents = Files.toString(file, Charset.forName("UTF-8"));
            }

            Gson gson = new Gson();
            Lecturer[] lecturerArray = gson.fromJson(contents, Lecturer[].class);
            List<Lecturer> lecturerList = new ArrayList<>();
            for (Lecturer lecturer: lecturerArray) {
                lecturer.setImg(Base64.decode(lecturer.getImage(), Base64.DEFAULT));
                lecturer.setTitle(lecturer.getTitle().replace("\\n","\n"));
                Log.i(TAG,lecturer.toString());

                lecturerList.add(lecturer);
                lecturers.put(lecturer.getName(),lecturer);
            }

            Collections.sort(lecturerList, new LecturerComparator());
            for (Lecturer lecturer: lecturerList) {
                lecturerNames.add(lecturer.getName());
                lecturerIds.add(lecturer.getId());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lecturerNames);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.lecturers = (HashMap<String, Lecturer>) lecturers.clone();
            JSONCache.lecturerNames = (ArrayList<String>) lecturerNames.clone();
            JSONCache.lecturerIds = (ArrayList<String>) lecturerIds.clone();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "fetchJSONRaw: Error= " + e.getMessage());
        }
    }

}
