package com.iho.asu.DisplayDataFromJSON;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iho.asu.Model.Travel;
import com.iho.asu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TravelFragment extends Fragment {

    private ArrayList<String> travelTitle = new ArrayList<String>();
    protected Map<String,Travel> travelItems = new HashMap<String, Travel>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_travel_lrn, container, false);

        return v;
    }

}
