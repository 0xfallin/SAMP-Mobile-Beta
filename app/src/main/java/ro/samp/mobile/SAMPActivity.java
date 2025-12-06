package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class SAMPActivity extends Activity {

    private SAMP samp = new SAMP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Optional: make fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Load native libraries only once
        SAMP.loadLibraries();

        // Initialize native storage and game
        samp.initNative();

        // Start the SAMP main loop
        samp.startSAMP();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        samp.stopSAMP();
    }
}
