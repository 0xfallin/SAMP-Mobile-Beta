package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText edtHost, edtPort, edtNick;
    private Button btnPlay;
    private SAMP samp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHost = findViewById(R.id.edtHost);
        edtPort = findViewById(R.id.edtPort);
        edtNick = findViewById(R.id.edtNick);
        btnPlay = findViewById(R.id.btnPlay);

        samp = new SAMP();

        btnPlay.setOnClickListener(v -> {
            btnPlay.setEnabled(false);
            Toast.makeText(this, "Starting SAMP...", Toast.LENGTH_SHORT).show();

            new Thread(() -> {
                try {
                    // Load libraries and pass app path
                    SAMP.loadLibraries(getFilesDir().getAbsolutePath());

                    // Start SAMP
                    samp.startSAMP();

                    runOnUiThread(() -> 
                        Toast.makeText(this, "SAMP started!", Toast.LENGTH_SHORT).show()
                    );
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                    runOnUiThread(() -> 
                        Toast.makeText(this, "Failed to load libraries!", Toast.LENGTH_LONG).show()
                    );
                } finally {
                    runOnUiThread(() -> btnPlay.setEnabled(true));
                }
            }).start();
        });
    }
}
