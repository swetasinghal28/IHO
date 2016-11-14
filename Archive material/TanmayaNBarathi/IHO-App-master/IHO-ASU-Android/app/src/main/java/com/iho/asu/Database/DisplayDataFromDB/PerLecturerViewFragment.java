package com.iho.asu.Database.DisplayDataFromDB;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iho.asu.Database.Columns;
import com.iho.asu.R;

import java.io.ByteArrayInputStream;


public class PerLecturerViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.per_lecturer, container, false);
        TextView textView = (TextView) v.findViewById(R.id.name);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);
        TextView textView1 = (TextView)v.findViewById(R.id.title);
        TextView textView2 = (TextView)v.findViewById(R.id.bio);
        Intent i = getActivity().getIntent();

        ByteArrayInputStream img = new ByteArrayInputStream(i.getByteArrayExtra(Columns.KEY_LECTURER_IMAGE.getColumnName()));
        Bitmap displayImg = BitmapFactory.decodeStream(img);
        imageView.setImageBitmap(displayImg);
        textView.setText(i.getStringExtra(Columns.KEY_LECTURER_NAME.getColumnName()));
        textView1.setText(i.getStringExtra(Columns.KEY_LECTURE_TITLE.getColumnName()));
        textView2.setText(i.getStringExtra(Columns.KEY_LECTURER_BIO.getColumnName()));
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

}