package com.iho.asu.Database.DisplayDataFromDB;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iho.asu.Database.Columns;
import com.iho.asu.Database.CustomList2;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.Travel;
import com.iho.asu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.iho.asu.IHOConstants.TRAVEL_URL;

public class TravelFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";

    private static final String TABLE_NAME = "TravelLearn";

    private SQLiteDatabase database;
    private ArrayList<String> travelTitle = new ArrayList<String>();
    protected Map<String,Travel> travelItems = new HashMap<String, Travel>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_travel_lrn, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        travelItems.clear();
        travelTitle.clear();
        getTravelItem();
        CustomList2 adapter = new
                CustomList2(this.getActivity(), travelTitle);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        String travel_text = travelTitle.get(position);
        Travel travel = travelItems.get(travel_text);
        Uri uri = Uri.parse(travel.getLink());
        Log.i("Travel: onListItemClick", uri.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void getTravelItem() {
        Travel n = new Travel();
        String text = "Click to read more";
        n.setId(1);
        n.setText(text);
        n.setLink(TRAVEL_URL);
        travelTitle.add(text);
        travelItems.put(text, n);
    }

    //Extracting elements from the database
    private void getTravelItems() {
        String[] columns = Columns.getTravelColumnNames();
        Cursor travelCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_TRAVEL_ID.getColumnName());
        travelCursor.moveToFirst();
        while (!travelCursor.isAfterLast()) {
            cursorToTravel(travelCursor);
            travelCursor.moveToNext();
        }
        travelCursor.close();
    }

    private void cursorToTravel(Cursor cursor) {
        Travel n = new Travel();
        String text = cursor.getString(1);
        n.setId(cursor.getLong(0));
        n.setText(text);
        n.setLink(cursor.getString(2));
        travelTitle.add(text);
        travelItems.put(text, n);
    }
}
