package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("GTASA");
        System.loadLibrary("samp");
    }

    // Start SAMP using settings.ini
    public native void startSAMP();

    // Save settings to settings.ini
    public native void saveSettings(String host, int port, String nickname);

    private EditText edtHost, edtPort, edtNick;
    private Button btnSave, btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHost = findViewById(R.id.edtHost);
        edtPort = findViewById(R.id.edtPort);
        edtNick = findViewById(R.id.edtNick);
        btnSave = findViewById(R.id.btnSave);
        btnPlay = findViewById(R.id.btnPlay);

        // Save settings to settings.ini
        btnSave.setOnClickListener(v -> {
            String host = edtHost.getText().toString();
            int port = Integer.parseInt(edtPort.getText().toString());
            String nick = edtNick.getText().toString();

            saveSettings(host, port, nick);

            // Optional: show feedback
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });

        // Start SAMP using saved settings
        btnPlay.setOnClickListener(v -> {
            btnPlay.setEnabled(false); // prevent multiple clicks

            new Thread(() -> {
                try {
                    startSAMP(); // heavy C++ initialization

                    // Optionally notify UI thread when ready
                    runOnUiThread(() -> 
                        Toast.makeText(this, "SAMP initialized!", Toast.LENGTH_SHORT).show()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> 
                        Toast.makeText(this, "Failed to start SAMP!", Toast.LENGTH_SHORT).show()
                    );
                } finally {
                    runOnUiThread(() -> btnPlay.setEnabled(true));
                }
            }).start();
        });
    }
}
