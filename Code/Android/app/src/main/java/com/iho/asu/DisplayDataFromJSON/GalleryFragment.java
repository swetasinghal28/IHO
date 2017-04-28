package com.iho.asu.DisplayDataFromJSON;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.iho.asu.Utilities.AppController;
import com.iho.asu.Comparators.ImageComparator;
import com.iho.asu.Utilities.CustomList;
import com.iho.asu.Model.Gallery;
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

import static com.iho.asu.Constants.IHOConstants.GALLERY_IDS;
import static com.iho.asu.Constants.IHOConstants.GALLERY_URL;
import static com.iho.asu.Constants.IHOConstants.IMAGE;
import static com.iho.asu.Constants.IHOConstants.IMAGE_ID;
import static com.iho.asu.Constants.IHOConstants.IMAGE_ORDER;
import static com.iho.asu.Constants.IHOConstants.IMAGE_TITLE;

public class GalleryFragment extends ListFragment {

    private static final String TAG = "Gallery";


    private ArrayList<String> galleryIds = new ArrayList<String>();
    private boolean isContentChanged = false;
    private File path = null;
    private File file = null;
    private HashMap<String, Gallery> galleryMap = new HashMap<String, Gallery>();


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.gallery, container, false);

        Context context = v.getContext();
        path = context.getFilesDir();
        file = new File(path, "gallery.json");
        View layout = inflater.inflate(
                R.layout.custom_toast, (ViewGroup) v.findViewById(R.id.toast_layout_root));


        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Loading...Please Wait!");

        Toast toast = new Toast(v.getContext());
        toast.setGravity((Gravity.AXIS_PULL_AFTER) << Gravity.AXIS_Y_SHIFT, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        Log.i(TAG, "fetching Contents...");
        if (JSONCache.galleryIds.size() == 0) {
            //Cache Empty, Fetch  News Objects
            Log.i(TAG,"Cache Empty, Fetch  News Objects...");
            getGalleryJson();
        } else {
            //Cache not empty, checking if contents are modified
            Log.i(TAG,"Cache not empty, checking if contents are modified...");
            getGalleryIdsJSON();
        }

        return v;
    }

    private void getGalleryJson() {
        Log.i(TAG, "getGalleryJson");
        isContentChanged = false;
        JsonArrayRequest request = new JsonArrayRequest(GALLERY_URL,
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
            String id = null, image = null, title = null, order = null;
            JSONCache.galleryMap.clear();
            JSONCache.galleryIds.clear();

            galleryIds.clear();
            galleryMap.clear();


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
                galleryMap.put(img.getId(), img);

            }

            List<Gallery> gallery = new ArrayList<Gallery>(galleryMap.values());
            Collections.sort(gallery, new ImageComparator());

            ArrayList<String> galleryTitle = new ArrayList<String>();
            ArrayList<byte[]> galleryItems = new ArrayList<byte[]>();

            for(Gallery img: gallery) {
                Log.i(TAG, img.toString());
                galleryItems.add(img.getImg());
                galleryTitle.add(img.getImageCaption());
                galleryIds.add(img.getId());
            }

            CustomList adapter = new
                    CustomList(this.getActivity(), galleryTitle, galleryItems);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");

            JSONCache.galleryIds = (ArrayList<String>) galleryIds.clone();
            JSONCache.galleryMap = (HashMap<String, Gallery>) galleryMap.clone();


        } catch (JSONException e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (IOException e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    private void getGalleryIdsJSON() {
        Log.i(TAG, "getGalleryIdsJSON");

        JsonArrayRequest request = new JsonArrayRequest(GALLERY_IDS.toString(),
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

            List<String> galleryIDs = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject img = jsonArray.getJSONObject(i);

                if (!img.isNull(IMAGE_ID)) {
                    id = img.getString(IMAGE_ID);
                }

                galleryIDs.add(id);
            }

            ArrayList<String> oldListIds = (ArrayList<String>) JSONCache.galleryIds.clone();
            if (oldListIds.size() == 0) {
                Log.i(TAG, "Local IdList is empty");
                isContentChanged = true;
            } else if (oldListIds.size() != galleryIDs.size()) {
                Log.i(TAG, "Local IdList Size: " + oldListIds.size());
                Log.i(TAG, "Server IdList Size: " + galleryIDs.size());
                isContentChanged = true;
            } else {
                for (String i: galleryIDs) {
                    if (!oldListIds.contains(i)) {
                        isContentChanged = true;
                        break;
                    }
                }
            }
            if (isContentChanged) {
                Log.i(TAG, "Content Changed on server, fetching from server...");
                getGalleryJson();
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
            galleryMap.clear();

            ArrayList<String> galleryTitle = new ArrayList<String>();
            ArrayList<byte[]> galleryItems = new ArrayList<byte[]>();

            galleryMap = (HashMap<String, Gallery>) JSONCache.galleryMap.clone();
            List<Gallery> imageList = new ArrayList<Gallery> (galleryMap.values());
            Collections.sort(imageList,  new ImageComparator());
            for (Gallery img: imageList) {
                galleryTitle.add(img.getImageCaption());
                galleryItems.add(img.getImg());
            }

            CustomList adapter = new
                    CustomList(this.getActivity(), galleryTitle, galleryItems);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e(TAG, "getJSONCache: Error=" + e.getMessage());
        }

    }

    private void fetchJSONRaw(){
        try {

            JSONCache.galleryMap.clear();
            JSONCache.galleryIds.clear();
            galleryMap.clear();

            ArrayList<String> galleryTitle = new ArrayList<String>();
            ArrayList<byte[]> galleryItems = new ArrayList<byte[]>();

            String contents = null;

            if (file.length() == 0) {
                Log.i(TAG,"File storage empty, fetching from resources...");
                JSONResourceReader jsonResourceReader = new JSONResourceReader(getResources(), R.raw.gallery);
                contents = jsonResourceReader.jsonString;
            } else {
                Log.i(TAG, "fetching JSON from filestorage");
                contents = Files.toString(file, Charset.forName("UTF-8"));
            }

            Gson gson = new Gson();
            Gallery[] imgArray = gson.fromJson(contents, Gallery[].class);


            for (Gallery img: imgArray) {
                img.setImg(Base64.decode(img.getImage(), Base64.DEFAULT));

                Log.i(TAG,img.toString());

                galleryMap.put(img.getId(),img);
                galleryIds.add(img.getId());
            }

            List<Gallery> imgList = new ArrayList<Gallery> (galleryMap.values());
            Collections.sort(imgList,  new ImageComparator());
            for (Gallery gallery: imgList) {
                galleryTitle.add(gallery.getImageCaption());
                galleryItems.add(gallery.getImg());
            }

            CustomList adapter = new
                    CustomList(this.getActivity(), galleryTitle, galleryItems);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.galleryMap = (HashMap<String, Gallery>) galleryMap.clone();
            JSONCache.galleryIds = (ArrayList<String>) galleryIds.clone();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "fetchJSONRaw: Error= " + e.getMessage());
        }
    }

}