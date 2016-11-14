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
import com.iho.asu.Database.Tables.Science;
import com.iho.asu.R;

import java.util.HashMap;
import java.util.Map;

public class ScienceFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "Science";

    private SQLiteDatabase database;
    int i = 0;
    private Map<String,String> scienceDetailedTitle = new HashMap<String, String>();
    protected Map<String,Science> scienceItems;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_science, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        scienceItems = new HashMap<String, Science>();
        scienceItems.clear();
        getScienceItems();
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, scienceDetailedTitle.keySet().toArray(new String[0]));
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String name = (scienceDetailedTitle.keySet().toArray(new String[0]))[position];
        Science science = scienceItems.get(scienceDetailedTitle.get(name));
        i.putExtra(Columns.KEY_SCIENCE_TITLE.getColumnName(), scienceDetailedTitle.get(name));
        i.putExtra(Columns.KEY_SCIENCE_LINK.getColumnName(),science.getLink());
        i.putExtra("ViewNeeded","Science");
        startActivity(i);
    }

    //Extracting elements from the database
    private void getScienceItems() {
        String[] columns = Columns.getScienceColumnNames();
        Cursor scienceCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_SCIENCE_ID.getColumnName());
        scienceCursor.moveToFirst();
        while (!scienceCursor.isAfterLast()) {
            cursorToScience(scienceCursor);
            scienceCursor.moveToNext();
        }
        scienceCursor.close();
    }

    private void cursorToScience(Cursor cursor) {
        Science n = new Science();
        String title = cursor.getString(1);
        n.setId(cursor.getLong(0));
        n.setTitle(title);
        n.setLink(cursor.getString(2));
        scienceItems.put(title, n);
        scienceDetailedTitle.put(title,title);
    }
}
