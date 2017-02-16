package com.iho.asu.Database;

/**
 * Created by Barathi on 10/18/2014.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iho.asu.R;

import java.util.ArrayList;

public class CustomList2 extends ArrayAdapter<String>{
    private final Activity context;
    private ArrayList<String> title = new ArrayList<String>();
    public CustomList2(Activity context,
                       ArrayList<String> title) {
        super(context, R.layout.list_single2, title);
        this.context = context;
        this.title = title;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single2, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        String ntitle = title.get(position);
        ntitle = (ntitle.length()<40)?ntitle:ntitle.substring(0,39)+"...  >";
        txtTitle.setText(ntitle);
        return rowView;
    }
}
