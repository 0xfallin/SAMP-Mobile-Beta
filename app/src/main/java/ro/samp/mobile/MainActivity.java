package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
        });

        // Start SAMP using saved settings
        btnPlay.setOnClickListener(v -> {
            new Thread(() -> startSAMP()).start();
        });
    }
}
