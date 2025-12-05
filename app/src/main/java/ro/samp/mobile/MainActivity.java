package ro.samp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText edtHost, edtPort, edtNick, edtPassword;
    private Button btnSave, btnPlay;

    private SAMP samp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // make sure layout exists

        // Initialize UI
        edtHost = findViewById(R.id.edtHost);
        edtPort = findViewById(R.id.edtPort);
        edtNick = findViewById(R.id.edtNick);
        edtPassword = findViewById(R.id.edtPassword);

        btnSave = findViewById(R.id.btnSave);
        btnPlay = findViewById(R.id.btnPlay);

        samp = new samp();

        // Save button (store settings locally)
        btnSave.setOnClickListener(v -> {
            String host = edtHost.getText().toString().trim();
            String port = edtPort.getText().toString().trim();
            String nick = edtNick.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(host.isEmpty() || port.isEmpty() || nick.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save to preferences
            getSharedPreferences("SAMP", MODE_PRIVATE).edit()
                    .putString("host", host)
                    .putString("port", port)
                    .putString("nick", nick)
                    .putString("password", password)
                    .apply();

            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
        });

        // Play button (launch SAMP)
        btnPlay.setOnClickListener(v -> {
            // Load saved settings
            String host = edtHost.getText().toString().trim();
            String port = edtPort.getText().toString().trim();
            String nick = edtNick.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(host.isEmpty() || port.isEmpty() || nick.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: set these values to CSettings in native code if needed

            // Launch SAMP
            samp.startSAMP();

            Toast.makeText(this, "SAMP launched!", Toast.LENGTH_SHORT).show();
        });

        // Load saved values
        edtHost.setText(getSharedPreferences("SAMP", MODE_PRIVATE).getString("host", "gtavnrp.com"));
        edtPort.setText(getSharedPreferences("SAMP", MODE_PRIVATE).getString("port", "7777"));
        edtNick.setText(getSharedPreferences("SAMP", MODE_PRIVATE).getString("nick", "Player"));
        edtPassword.setText(getSharedPreferences("SAMP", MODE_PRIVATE).getString("password", ""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        samp.stopSAMP(); // stop SAMP when activity is destroyed
    }
}
