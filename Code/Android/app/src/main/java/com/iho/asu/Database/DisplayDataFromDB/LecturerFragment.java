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
import com.iho.asu.Database.Tables.Lecturer;
import com.iho.asu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LecturerFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "Lecturer";

    private SQLiteDatabase database;
    private ArrayList<String> lecturerNames = new ArrayList<String>();
    protected Map<String,Lecturer> lecturers = new HashMap<String, Lecturer>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_lecturer, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        lecturerNames.clear();
        lecturers.clear();
        getLecturers();
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lecturerNames);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String name = lecturerNames.get(position);
        Lecturer lecturer = lecturers.get(name);
        i.putExtra(Columns.KEY_LECTURER_NAME.getColumnName(), name);
        i.putExtra(Columns.KEY_LECTURER_IMAGE.getColumnName(),lecturer.getImage());
        i.putExtra(Columns.KEY_LECTURER_BIO.getColumnName(),lecturer.getBio());
        i.putExtra(Columns.KEY_LECTURER_EMAIL.getColumnName(),lecturer.getEmail());
        i.putExtra(Columns.KEY_LECTURE_TITLE.getColumnName(),lecturer.getTitle());
        i.putExtra(Columns.KEY_LECTURER_LINK.getColumnName(),lecturer.getLink());
        i.putExtra("ViewNeeded","Lecturer");
        startActivity(i);
    }

    //Extracting elements from the database
    private void getLecturers() {
        String[] columns = Columns.getLecturerColumnNames();
        Cursor lecCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_LECTURER_ID.getColumnName());
        lecCursor.moveToFirst();
        while (!lecCursor.isAfterLast()) {
            cursorToLecturer(lecCursor);
            lecCursor.moveToNext();
        }
        lecCursor.close();
    }

    private void cursorToLecturer(Cursor cursor) {
        Lecturer l = new Lecturer();
        String name = cursor.getString(1);
        l.setId(cursor.getLong(0));
        l.setName(name);
        l.setImage(cursor.getBlob(2));
        l.setBio(cursor.getString(3));
        l.setEmail(cursor.getString(4));
        l.setLink(cursor.getString(5));
        l.setTitle(cursor.getString(6));
        lecturerNames.add(name);
        lecturers.put(name,l);
    }
}
