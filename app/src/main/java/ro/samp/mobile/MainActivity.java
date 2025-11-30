package ro.samp.mobile;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SAMPLauncher";
    private FileLogger fileLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize file logger
        fileLogger = new FileLogger(this, "samp_logs.txt");

        // Load native libraries and log to file
        loadLibraryWithLog("GTASA"); // loads libGTASA.so
        loadLibraryWithLog("samp");  // loads libsamp.so

        fileLogger.log("MainActivity created successfully");
    }

    /**
     * Helper method to load native libraries and log success/failure
     */
    private void loadLibraryWithLog(String libName) {
        try {
            System.loadLibrary(libName);
            String msg = libName + ".so loaded successfully";
            Log.d(TAG, msg);
            fileLogger.log(msg);
        } catch (UnsatisfiedLinkError e) {
            String msg = "Failed to load " + libName + ".so: " + e.getMessage();
            Log.e(TAG, msg, e);
            fileLogger.log(msg);
        }
    }

    // Example native function
    public native void initSamp();
}
