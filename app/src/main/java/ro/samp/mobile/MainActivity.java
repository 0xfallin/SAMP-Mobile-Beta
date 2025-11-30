package ro.samp.mobile;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SAMPLauncher";
    private FileLogger fileLogger;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileLogger = new FileLogger(this, "samp_logs.txt");

        fileLogger.log("MainActivity started");
        Log.d(TAG, "MainActivity started");

        // Load libs in background to avoid UI freeze
        new Thread(() -> {
            loadLibraryWithLog("GTASA");  // loads libGTASA.so
            loadLibraryWithLog("samp");   // loads libsamp.so

            fileLogger.log("All libs loaded (background thread)");
            Log.d(TAG, "All libs loaded (background thread)");

            // Update UI after loading
            runOnUiThread(() -> tvStatus.setText("SAMP Mobile Launcher Ready"));
        }).start();
    }

    /**
     * Helper method to load native libraries and log success/failure
     */
    private void loadLibraryWithLog(String libName) {
        try {
            System.loadLibrary(libName);
            String msg = "Loaded lib: " + libName + ".so";
            Log.d(TAG, msg);
            fileLogger.log(msg);
        } catch (UnsatisfiedLinkError e) {
            String msg = "FAILED to load " + libName + ".so → " + e.getMessage();
            Log.e(TAG, msg, e);
            fileLogger.log(msg);
        }
    }

    // Example native function
    public native void initSamp();
}
