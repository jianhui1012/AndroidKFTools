package com.tools.kf.gisandroidmap;

import android.os.Bundle;


import com.tools.kf.anotation.ContentView;
import com.tools.kf.ui.BasicActivity;

@ContentView(R.layout.activity_main2)
public class Main2Activity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGuesture(true);

    }

}
