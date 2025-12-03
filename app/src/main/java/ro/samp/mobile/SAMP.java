package com.samp.launcher;

import android.os.Bundle;
import android.util.Log;

public class SAMP extends GTASA {

    private static SAMP instance;
    private static final String TAG = "SAMP";

    public static SAMP getInstance() {
        return instance;
    }

    static {
        System.loadLibrary("samp");
    }

    public native void sendDialogResponse(int dialogId, int btnId, int listId, byte[] str);
    public native void initializeSAMP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        try {
            initializeSAMP();
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "JNI init failed: " + e.getMessage());
        }
    }
}
