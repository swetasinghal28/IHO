package com.iho.asu.Pages;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iho.asu.R;


public class FieldNotes extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       return(inflater.inflate(
                R.layout.fragment_field, container, false));
    }

    @Override
    public void onStart(){
        super.onStart();
    }
}