package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText edtHost, edtPort, edtNick, edtPassword;
    private Button btnSave, btnPlay;
    private SAMP samp = new SAMP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHost = findViewById(R.id.edtHost);
        edtPort = findViewById(R.id.edtPort);
        edtNick = findViewById(R.id.edtNick);
        edtPassword = findViewById(R.id.edtPassword);
        btnSave = findViewById(R.id.btnSave);
        btnPlay = findViewById(R.id.btnPlay);

        edtHost.setText(getPref("host", "gtavnrp.com"));
        edtPort.setText(getPref("port", "7777"));
        edtNick.setText(getPref("nick", "Player"));
        edtPassword.setText(getPref("password", ""));

        btnSave.setOnClickListener(v -> saveSettings());
        btnPlay.setOnClickListener(v -> launchGame());
    }

    private void saveSettings() {
        putPref("host", edtHost.getText().toString());
        putPref("port", edtPort.getText().toString());
        putPref("nick", edtNick.getText().toString());
        putPref("password", edtPassword.getText().toString());
        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
    }

    private void launchGame() {

        String host = edtHost.getText().toString();
        String port = edtPort.getText().toString();
        String nick = edtNick.getText().toString();

        if (host.isEmpty() || port.isEmpty() || nick.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String storagePath = getFilesDir().getAbsolutePath() + "/samp/";
        SAMP.loadLibraries(storagePath);
        
        // Initialize native libraries and storage paths
        samp.initNative();

        Toast.makeText(this, "Launching SAMP...", Toast.LENGTH_SHORT).show();
    }

    private String getPref(String k, String def) {
        return getSharedPreferences("SAMP", MODE_PRIVATE).getString(k, def);
    }

    private void putPref(String k, String v) {
        getSharedPreferences("SAMP", MODE_PRIVATE).edit().putString(k, v).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        samp.stopSAMP();
    }
}
