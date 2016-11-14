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
import com.iho.asu.Database.Tables.News;
import com.iho.asu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsFragment extends ListFragment {

    private static final String DB_NAME = "asuIHO.db";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "News";

    private SQLiteDatabase database;
    private ArrayList<String> newsTitle = new ArrayList<String>();
    protected Map<String,News> newsItems = new HashMap<String, News>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_news, container, false);
        DataBaseHelper dbOpenHelper = new DataBaseHelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        newsItems.clear();
        newsTitle.clear();
        getNewsItems();
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
        this.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent i= new Intent(this.getActivity(),ViewActivity.class );
        String name = newsTitle.get(position);
        News news = newsItems.get(name);
        i.putExtra(Columns.KEY_NEWS_TITLE.getColumnName(), name);
        i.putExtra(Columns.KEY_NEWS_IMAGE.getColumnName(),news.getImage());
        i.putExtra(Columns.KEY_NEWS_LINK.getColumnName(),news.getNewsLink());
        i.putExtra(Columns.KEY_NEWS_TEXT.getColumnName(),news.getText());
        i.putExtra("ViewNeeded","News");
        startActivity(i);
    }

    //Extracting elements from the database
    private void getNewsItems() {
        String[] columns = Columns.getNewsColumnNames();
        Cursor newsCursor = database.query(TABLE_NAME, columns, null, null, null, null, Columns.KEY_NEWS_ID.getColumnName());
        newsCursor.moveToFirst();
        while (!newsCursor.isAfterLast()) {
            cursorToNews(newsCursor);
            newsCursor.moveToNext();
        }
        newsCursor.close();
    }

    private void cursorToNews(Cursor cursor) {
        News n = new News();
        String title = cursor.getString(1);
        n.setId(cursor.getLong(0));
        n.setTitle(title);
        n.setText(cursor.getString(2));
        n.setImage(cursor.getBlob(3));
        n.setNewsLink(cursor.getString(4));
        newsTitle.add(title);
        newsItems.put(title, n);
    }

}
