package com.iho.asu.Database.DisplayDataFromDB;

import android.app.ListFragment;
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
import com.iho.asu.AppController;
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

    private static final String TAG = "Science";

    int i = 0;
    private Map<String,String> scienceDetailedTitle = new HashMap<String, String>();
    private ArrayList<String> scienceTitle = new ArrayList<String>();
    protected Map<String,Science> scienceItems = new HashMap<String, Science>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_science, container, false);


        getScienceJson();

        View layout = inflater.inflate(
                R.layout.custom_toast, (ViewGroup) v.findViewById(R.id.toast_layout_root));


        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Click on item to read more!");

        Toast toast = new Toast(v.getContext());
        toast.setGravity((Gravity.AXIS_PULL_AFTER)<<Gravity.AXIS_Y_SHIFT,0,100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        for (int i = 1; i<=2; i++) {toast.show();}

        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        String title = scienceTitle.get(position);
        Science science = scienceItems.get(title);
        Uri uri = Uri.parse(science.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
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


}
