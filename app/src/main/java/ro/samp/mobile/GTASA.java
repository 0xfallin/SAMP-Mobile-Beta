package com.samp.launcher;

import android.app.Activity;
import android.os.Bundle;

public class GTASA extends Activity {

    static {
        System.loadLibrary("GTASA");
        System.loadLibrary("samp");
    }

    public native void setCurrentScreenSize(int width, int height);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
