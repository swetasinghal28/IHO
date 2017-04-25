package com.iho.asu.DisplayDataFromJSON;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iho.asu.Utilities.AppController;
import com.iho.asu.Comparators.ImageComparator;
import com.iho.asu.Utilities.CustomList;
import com.iho.asu.Tables.Gallery;
import com.iho.asu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.iho.asu.Constants.IHOConstants.GALLERY_URL;
import static com.iho.asu.Constants.IHOConstants.IMAGE;
import static com.iho.asu.Constants.IHOConstants.IMAGE_ID;
import static com.iho.asu.Constants.IHOConstants.IMAGE_ORDER;
import static com.iho.asu.Constants.IHOConstants.IMAGE_TITLE;
import static com.iho.asu.Constants.IHOConstants.LECTURER_GALLERY_KEY;
import static com.iho.asu.Constants.IHOConstants.LECTURER_GALLERY_URL;

public class LecturerGalleryFragment extends ListFragment {

    private static final String TAG = "Lecturer Gallery";

    private String LectKey = "";
    private ArrayList<String> galleryTitle = new ArrayList<String>();
    private ArrayList<byte[]> galleryItems = new ArrayList<byte[]>();


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        try {
            Bundle bundle = this.getArguments();
            this.LectKey = bundle.getString(LECTURER_GALLERY_KEY);

            v = inflater.inflate(
                    R.layout.fragment_lecturer_gallery, container, false);
            getGalleryItemsJson1();
            CustomList adapter = new
                    CustomList(this.getActivity(), galleryTitle, galleryItems);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {

            e.printStackTrace();
            Log.e(TAG, "onCreateView: Error=" + e.getMessage());
        }
        return v;
    }

    private void getGalleryItemsJson() {
        Log.i(TAG, "getGalleryItemsJson");
        String URL = LECTURER_GALLERY_URL + LectKey;
        Log.i(TAG, URL);
        String tempURL = GALLERY_URL;
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray result) {

                        Log.i(TAG, "onResponse: Result = ");
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

    private void getGalleryItemsJson1() {
        Log.i(TAG, "getGalleryItemsJson1");
        String URL = LECTURER_GALLERY_URL + LectKey;
        Log.i(TAG, URL);
        String tempURL = GALLERY_URL;
        JsonObjectRequest request = new JsonObjectRequest(URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {

                        Log.i(TAG, "onResponse: Result = " + result);
                        parseJSONResult1(result);
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
            String id = null, image = null, title = null, order = null;


            List<Gallery> gallery = new ArrayList<Gallery>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                if (!obj.isNull(IMAGE_ID)) {
                    id = obj.getString(IMAGE_ID);
                }

                if (!obj.isNull(IMAGE_TITLE)) {
                    title = obj.getString(IMAGE_TITLE);
                }

                if (!obj.isNull(IMAGE)) {
                    image = obj.getString(IMAGE);
                }

                if (!obj.isNull(IMAGE_ORDER)) {
                    order = obj.getString(IMAGE_ORDER);
                }

                Gallery img = new Gallery();
                img.setId(id);
                img.setImageCaption(title);
                img.setImg(Base64.decode(image, Base64.DEFAULT));
                img.setOrder(Integer.parseInt(order));

                //Log.i(TAG, i + ": " + img.toString());

                gallery.add(img);

            }

            Collections.sort(gallery, new ImageComparator());
            for(Gallery img: gallery) {
                Log.i(TAG, img.toString());
                galleryItems.add(img.getImg());
                galleryTitle.add(img.getImageCaption());
            }

            CustomList adapter = new
                    CustomList(this.getActivity(), galleryTitle, galleryItems);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    private void parseJSONResult1(JSONObject jsonObject) {
        try {

            Log.i(TAG, "parseJSONResult");
            String id = null, image = null, title = null, order = null;


            List<Gallery> gallery = new ArrayList<Gallery>();
            JSONArray jsonArray = jsonObject.getJSONArray("imagesarray");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                if (!obj.isNull(IMAGE_ID)) {
                    id = obj.getString(IMAGE_ID);
                }

                if (!obj.isNull(IMAGE_TITLE)) {
                    title = obj.getString(IMAGE_TITLE);
                }

                if (!obj.isNull(IMAGE)) {
                    image = obj.getString(IMAGE);
                }

                if (!obj.isNull(IMAGE_ORDER)) {
                    order = obj.getString(IMAGE_ORDER);
                }

                Gallery img = new Gallery();
                img.setId(id);
                img.setImageCaption(title);
                img.setImg(Base64.decode(image, Base64.DEFAULT));
                img.setOrder(Integer.parseInt(order));

                //Log.i(TAG, i + ": " + img.toString());

                gallery.add(img);

            }

            Collections.sort(gallery, new ImageComparator());
            for(Gallery img: gallery) {
                Log.i(TAG, img.toString());
                galleryItems.add(img.getImg());
                galleryTitle.add(img.getImageCaption());
            }

            CustomList adapter = new
                    CustomList(this.getActivity(), galleryTitle, galleryItems);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {

            e.printStackTrace();
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

}