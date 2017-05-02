package com.iho.asu.DisplayDataFromJSON;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.common.io.Files;
import com.iho.asu.Model.News;
import com.iho.asu.R;
import com.iho.asu.Utilities.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.iho.asu.Constants.IHOConstants.FEATURED_NEWS_URL;
import static com.iho.asu.Constants.IHOConstants.NEWS_DATE;
import static com.iho.asu.Constants.IHOConstants.NEWS_DESC;
import static com.iho.asu.Constants.IHOConstants.NEWS_ID;
import static com.iho.asu.Constants.IHOConstants.NEWS_IMAGE;
import static com.iho.asu.Constants.IHOConstants.NEWS_LINK;
import static com.iho.asu.Constants.IHOConstants.NEWS_TITLE;


public class PerFeaturedNewsViewFragment extends Fragment {

    public News fNews = new News();
    private static final String TAG = "FeaturedNews";

    private File path = null;
    private File file = null;
    ImageView imageView = null;
    TextView textView = null;
    TextView textView1 = null;
    Button button = null;
    String link = null;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = null;
        try {

            v = inflater.inflate(
                    R.layout.per_featurednews_item, container, false);
            imageView = (ImageView) v.findViewById(R.id.image);
            textView = (TextView) v.findViewById(R.id.title);
            textView1 = (TextView)v.findViewById(R.id.text);
            button = (Button) v.findViewById(R.id.fnewsLink);

            Context context = v.getContext();
            path = context.getFilesDir();
            file = new File(path, "featured_news.json");

            View.OnClickListener buttonListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            };
            button.setOnClickListener(buttonListener);

            Log.i(TAG, "fetching Contents...");
            getNewsObjectJson();

        } catch (Exception e) {

        }

        return v;

    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    private void getNewsObjectJson() {
        Log.i(TAG, "getNewsObjectJson");
        JsonArrayRequest request = new JsonArrayRequest(FEATURED_NEWS_URL,
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
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {
            fNews = null;
            Log.i(TAG, "writing JSONArray to file storage");
            Files.write(jsonArray.toString().getBytes(), file);

            Log.i(TAG, "parseJSONResult");
            String id = null, title = null, newsDesc = null, newsLink = null, image = null, dateStr = null;


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


                fNews = new News();
                fNews.setId(id);
                fNews.setTitle(title);
                fNews.setText(newsDesc);
                fNews.setImg(Base64.decode(image, Base64.DEFAULT));
                fNews.setNewsLink(newsLink);
                fNews.setCreationDate(new Date(dateStr));

                Log.i(TAG, i + ": " + fNews.toString());

            }

            ByteArrayInputStream img = new ByteArrayInputStream(fNews.getImg());
            Bitmap displayImg = BitmapFactory.decodeStream(img);
            imageView.setImageBitmap(displayImg);
            textView.setText(fNews.getTitle());
            textView1.setText(fNews.getText());

            link = fNews.getNewsLink();
            Log.i(TAG, "Setting button link: " + link);


        } catch (JSONException e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

}