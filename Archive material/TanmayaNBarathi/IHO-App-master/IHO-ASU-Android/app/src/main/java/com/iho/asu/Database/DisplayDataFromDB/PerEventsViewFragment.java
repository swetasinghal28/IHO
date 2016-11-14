package com.iho.asu.Database.DisplayDataFromDB;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iho.asu.Database.Columns;
import com.iho.asu.R;


public class PerEventsViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.per_event, container, false);
        TextView textView = (TextView) v.findViewById(R.id.title);
        TextView textView1 = (TextView) v.findViewById(R.id.when);
        TextView textView2 = (TextView)v.findViewById(R.id.where);
        TextView textView3 = (TextView)v.findViewById(R.id.description);
        Intent i = getActivity().getIntent();
        textView.setText(i.getStringExtra(Columns.KEY_EVENT_TITLE.getColumnName()));
        textView1.setText(i.getStringExtra(Columns.KEY_EVENT_WHEN.getColumnName()));
        textView2.setText(i.getStringExtra(Columns.KEY_EVENT_WHERE.getColumnName()));
        textView3.setText(i.getStringExtra(Columns.KEY_EVENT_DESC.getColumnName()));
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

}