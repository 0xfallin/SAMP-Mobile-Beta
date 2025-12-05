package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    static {
        try {
            System.loadLibrary("GTASA");
            System.loadLibrary("samp");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    // Native methods
    public native void startSAMP();
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

        // Save settings button
        btnSave.setOnClickListener(v -> {
            String host = edtHost.getText().toString();
            String portText = edtPort.getText().toString();
            String nick = edtNick.getText().toString();

            if (host.isEmpty() || portText.isEmpty() || nick.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            int port;
            try {
                port = Integer.parseInt(portText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Port must be a number!", Toast.LENGTH_SHORT).show();
                return;
            }

            saveSettings(host, port, nick);
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });

        // Play button to start SAMP
        btnPlay.setOnClickListener(v -> {
            btnPlay.setEnabled(false); // prevent multiple clicks
            Toast.makeText(this, "Starting SAMP...", Toast.LENGTH_SHORT).show();

            new Thread(() -> {
                try {
                    startSAMP(); // call native code

                    runOnUiThread(() ->
                        Toast.makeText(this, "SAMP started!", Toast.LENGTH_SHORT).show()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                        Toast.makeText(this, "Failed to start SAMP!", Toast.LENGTH_LONG).show()
                    );
                } finally {
                    runOnUiThread(() -> btnPlay.setEnabled(true));
                }
            }).start();
        });
    }
}
