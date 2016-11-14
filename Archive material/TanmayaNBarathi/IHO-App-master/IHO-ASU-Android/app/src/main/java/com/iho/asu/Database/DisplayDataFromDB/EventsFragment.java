package com.iho.asu.Database.DisplayDataFromDB;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iho.asu.Database.Columns;
import com.iho.asu.Database.DataBaseHelper;
import com.iho.asu.Database.Tables.Events;
import com.iho.asu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventsFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "Events";

    private SQLiteDatabase database;
    private ArrayList<String> eventsTitle = new ArrayList<String>();
    protected Map<String,Events> eventsItems = new HashMap<String, Events>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_events, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        eventsItems.clear();
        eventsTitle.clear();
        getEventsItems();
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, eventsTitle);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String name = eventsTitle.get(position);
        Events events = eventsItems.get(name);
        i.putExtra(Columns.KEY_EVENT_TITLE.getColumnName(), name);
        i.putExtra(Columns.KEY_EVENT_WHEN.getColumnName(),events.getWhen());
        i.putExtra(Columns.KEY_EVENT_MAP.getColumnName(),events.getLocation_link());
        i.putExtra(Columns.KEY_EVENT_WHERE.getColumnName(),events.getWhere());
        i.putExtra(Columns.KEY_EVENT_DESC.getColumnName(),events.getDescription());
        i.putExtra(Columns.KEY_EVENT_REG.getColumnName(),events.getReg());
        i.putExtra("ViewNeeded","Events");
        startActivity(i);
    }

    //Extracting elements from the database
    private void getEventsItems() {
        String[] columns = Columns.getEventColumnNames();
        Cursor eventsCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_EVENT_ID.getColumnName());
        eventsCursor.moveToFirst();
        while (!eventsCursor.isAfterLast()) {
            cursorToEvents(eventsCursor);
            eventsCursor.moveToNext();
        }
        eventsCursor.close();
    }

    private void cursorToEvents(Cursor cursor) {
        Events n = new Events();
        String title = cursor.getString(4);
        n.setId(cursor.getLong(0));
        n.setTitle(title);
        n.setWhen(cursor.getString(1));
        n.setLocation_link(cursor.getString(2));
        n.setWhere(cursor.getString(3));
        n.setDescription(cursor.getString(5));
        n.setReg(cursor.getString(6));
        eventsTitle.add(title);
        eventsItems.put(title, n);
    }
}
