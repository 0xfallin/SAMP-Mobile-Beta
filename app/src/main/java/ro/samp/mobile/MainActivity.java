package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText edtHost, edtPort, edtNick, edtPassword;
    private Button btnSave, btnPlay;

    private SAMP samp; // make sure SAMP is a valid Java class with native methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // ensure this layout exists

        // Initialize UI elements
        edtHost = findViewById(R.id.edtHost);
        edtPort = findViewById(R.id.edtPort);
        edtNick = findViewById(R.id.edtNick);
        edtPassword = findViewById(R.id.edtPassword);

        btnSave = findViewById(R.id.btnSave);
        btnPlay = findViewById(R.id.btnPlay);

        // Initialize SAMP JNI wrapper
        samp = new SAMP();

        // Load saved values
        edtHost.setText(getSharedPreferences("SAMP", MODE_PRIVATE).getString("host", "gtavnrp.com"));
        edtPort.setText(getSharedPreferences("SAMP", MODE_PRIVATE).getString("port", "7777"));
        edtNick.setText(getSharedPreferences("SAMP", MODE_PRIVATE).getString("nick", "Player"));
        edtPassword.setText(getSharedPreferences("SAMP", MODE_PRIVATE).getString("password", ""));

        // Save button
        btnSave.setOnClickListener(v -> {
            String host = edtHost.getText().toString().trim();
            String port = edtPort.getText().toString().trim();
            String nick = edtNick.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(host.isEmpty() || port.isEmpty() || nick.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            getSharedPreferences("SAMP", MODE_PRIVATE).edit()
                    .putString("host", host)
                    .putString("port", port)
                    .putString("nick", nick)
                    .putString("password", password)
                    .apply();

            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
        });

        // Play button
        btnPlay.setOnClickListener(v -> {
            String host = edtHost.getText().toString().trim();
            String port = edtPort.getText().toString().trim();
            String nick = edtNick.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(host.isEmpty() || port.isEmpty() || nick.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call JNI method to set host/port/nick/password in native code if needed
            //samp.setServerSettings(host, port, nick, password);

            // Launch SAMP
            samp.startSAMP();

            Toast.makeText(this, "SAMP launched!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        samp.stopSAMP(); // stop SAMP on activity destroy
    }
}
