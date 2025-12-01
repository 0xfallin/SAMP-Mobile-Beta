package ro.samp.mobile;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class GTASA extends android.app.Activity {

    private static boolean once = false;
    private static String vmVersion;

    static {
        // Log VM version and load libraries
        try {
            vmVersion = System.getProperty("java.vm.version");
            Log.d("GTASA", "vmVersion: " + vmVersion);

            // Load native libraries in order
            System.loadLibrary("GTASA");   // Main GTA SA lib
            System.loadLibrary("bass");    // Audio library
            System.loadLibrary("SAMP");    // SAMP client
        } catch (UnsatisfiedLinkError e) {
            Log.e("GTASA", "Failed to load native libraries", e);
        }
    }

    // Native functions
    public native void main();
    public native void setCurrentScreenSize(int width, int height);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!once) {
            once = true;
            Log.d("GTASA", "GTASA onCreate");
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("GTASA", "GTASA onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("GTASA", "GTASA onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GTASA", "GTASA onResume");
    }

    @Override
    protected void onPause() {
        Log.d("GTASA", "GTASA onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("GTASA", "GTASA onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("GTASA", "GTASA onRestart");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("GTASA", "Configuration changed");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    // Optional stub functions (if needed by old wrapper)
    public void EnterSocialClub() {}
    public void ExitSocialClub() {}
    public boolean ServiceAppCommand(String cmd, String value) { return false; }
    public int ServiceAppCommandValue(String cmd, String value) { return 0; }

}
