package com.iho.asu.Database.DisplayDataFromDB;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.iho.asu.AppController;
import com.iho.asu.Comparators.LecturerComparator;
import com.iho.asu.Database.Columns;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.Lecturer;
import com.iho.asu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iho.asu.IHOConstants.LECTURER_BIO;
import static com.iho.asu.IHOConstants.LECTURER_EMAIL;
import static com.iho.asu.IHOConstants.LECTURER_ID;
import static com.iho.asu.IHOConstants.LECTURER_IMAGE;
import static com.iho.asu.IHOConstants.LECTURER_LINK;
import static com.iho.asu.IHOConstants.LECTURER_NAME;
import static com.iho.asu.IHOConstants.LECTURER_ORDER;
import static com.iho.asu.IHOConstants.LECTURER_TITLE;
import static com.iho.asu.IHOConstants.LECTURER_URL;


public class LecturerFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "Lecturer";
    private static final String TAG = "Lecturer";

    private SQLiteDatabase database;
    private ArrayList<String> lecturerNames = new ArrayList<String>();
    protected Map<String,Lecturer> lecturers = new HashMap<String, Lecturer>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_lecturer, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        lecturerNames.clear();
        lecturers.clear();
        //getLecturers();
        getLecturesJson();
        //ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lecturerNames);
        //this.setListAdapter(adapter);
        //adapter.notifyDataSetChanged();
        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String name = lecturerNames.get(position);
        Lecturer lecturer = lecturers.get(name);
        i.putExtra(Columns.KEY_LECTURER_NAME.getColumnName(), name);
        i.putExtra(Columns.KEY_LECTURER_IMAGE.getColumnName(),lecturer.getImage());
        i.putExtra(Columns.KEY_LECTURER_BIO.getColumnName(),lecturer.getBio());
        i.putExtra(Columns.KEY_LECTURER_EMAIL.getColumnName(),lecturer.getEmail());
        i.putExtra(Columns.KEY_LECTURE_TITLE.getColumnName(),lecturer.getTitle());
        i.putExtra(Columns.KEY_LECTURER_LINK.getColumnName(),lecturer.getLink());
        i.putExtra("ViewNeeded","Lecturer");
        startActivity(i);
    }

    private void getLecturesJson() {
        Log.i(TAG, "getLecturesJson");

        JsonArrayRequest request = new JsonArrayRequest(LECTURER_URL.toString(),
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
            newsTitle.add(news.getTitle());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();*/
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {

            Log.i(TAG, "parseJSONResult");
            String id = null, title = null, name = null, bio = null, link = null, image = null, email = null, order = null;


            List<Lecturer> lectList = new ArrayList<Lecturer>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject lecturer = jsonArray.getJSONObject(i);

                if (!lecturer.isNull(LECTURER_ID)) {
                    id = lecturer.getString(LECTURER_ID);
                }

                if (!lecturer.isNull(LECTURER_TITLE)) {
                    title = lecturer.getString(LECTURER_TITLE);
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
                l.setImage(Base64.decode(image, Base64.DEFAULT));
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
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lecturerNames);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }
    //Extracting elements from the database
    private void getLecturers() {
        String[] columns = Columns.getLecturerColumnNames();
        Cursor lecCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_LECTURER_ID.getColumnName());
        lecCursor.moveToFirst();
        while (!lecCursor.isAfterLast()) {
            cursorToLecturer(lecCursor);
            lecCursor.moveToNext();
        }
        lecCursor.close();
    }

    private void cursorToLecturer(Cursor cursor) {
        Lecturer l = new Lecturer();
        String name = cursor.getString(1);
        //l.setId(cursor.getLong(0));
        l.setName(name);
        l.setImage(cursor.getBlob(2));
        l.setBio(cursor.getString(3));
        l.setEmail(cursor.getString(4));
        l.setLink(cursor.getString(5));
        l.setTitle(cursor.getString(6));
        lecturerNames.add(name);
        lecturers.put(name,l);
    }
}
