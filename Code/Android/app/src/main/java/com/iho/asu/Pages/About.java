package com.iho.asu.Pages;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iho.asu.R;


public class About extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(
                R.layout.fragment_about, container, false);
        //String value = "<a href=\"https://iho.asu.edu/\">The Institute of Human Origins </a>is one of the preeminent research organizations in the world devoted to the science of human origins. IHO pursues an integrative strategy for research and discovery central to its over 30-year-old founding mission, bridging social, earth, and life science approaches to the most important questions concerning the course, causes, and timing of events in the human career over deep time. IHO fosters public awareness of human origins and its relevance to contemporary society through innovative outreach programs that create timely, accurate information for both education and lay communities."
          //      +"The Institute of Human Origins is a research center of the <a href=\"https://clas.asu.edu/\">College of Liberal Arts and Sciences</a> in the <a href=\"https://shesc.asu.edu/\">School of Human Evolution and Social Change</a> at Arizona State University.</string>";
        //String value = R.string.aboutInfo;
        TextView t2 = (TextView) v.findViewById(R.id.aboutInfoView);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

}