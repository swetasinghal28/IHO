package com.iho.asu.DisplayDataFromJSON;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iho.asu.Constants.FragmentFieldsMapping;
import com.iho.asu.R;

import java.io.ByteArrayInputStream;


public class PerNewsViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.per_news_item, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);
        TextView textView = (TextView) v.findViewById(R.id.title);
        TextView textView1 = (TextView)v.findViewById(R.id.text);
        Intent i = getActivity().getIntent();
        ByteArrayInputStream img = new ByteArrayInputStream(i.getByteArrayExtra(FragmentFieldsMapping.KEY_NEWS_IMAGE.getColumnName()));
        Bitmap displayImg = BitmapFactory.decodeStream(img);
        imageView.setImageBitmap(displayImg);
        textView.setText(i.getStringExtra(FragmentFieldsMapping.KEY_NEWS_TITLE.getColumnName()));
        textView1.setText(i.getStringExtra(FragmentFieldsMapping.KEY_NEWS_TEXT.getColumnName()));
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

}