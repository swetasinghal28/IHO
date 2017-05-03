package com.iho.asu.Pages;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Connect extends Fragment{

    public int resource;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        this.resource = bundle.getInt("resource");
        return inflater.inflate(
                resource, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

}