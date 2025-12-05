package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText edtHost, edtPort, edtNick;
    private Button btnSave, btnPlay;

    private SAMP samp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load native libraries safely (static call)
        SAMP.loadLibraries();

        edtHost = findViewById(R.id.edtHost);
        edtPort = findViewById(R.id.edtPort);
        edtNick = findViewById(R.id.edtNick);
        btnSave = findViewById(R.id.btnSave);
        btnPlay = findViewById(R.id.btnPlay);

        samp = new SAMP(); // safe now, libraries already loaded

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

            samp.saveSettings(host, port, nick);
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });

        btnPlay.setOnClickListener(v -> {
            btnPlay.setEnabled(false);
            Toast.makeText(this, "Starting SAMP...", Toast.LENGTH_SHORT).show();

            new Thread(() -> {
                try {
                    samp.startSAMP(); // libraries already loaded

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
