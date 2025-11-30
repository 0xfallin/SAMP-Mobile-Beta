package ro.samp.mobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SAMPLauncher sampLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampLauncher = new SAMPLauncher();

        EditText nickEdit = findViewById(R.id.nickEdit);
        EditText ipEdit = findViewById(R.id.ipEdit);
        EditText portEdit = findViewById(R.id.portEdit);
        EditText passwordEdit = findViewById(R.id.passwordEdit);
        Button connectBtn = findViewById(R.id.connectBtn);

        connectBtn.setOnClickListener(v -> {
            String nick = nickEdit.getText().toString();
            String ip = ipEdit.getText().toString();
            int port = Integer.parseInt(portEdit.getText().toString());
            String pass = passwordEdit.getText().toString();

            sampLauncher.setNick(nick);
            sampLauncher.setServerIP(ip);
            sampLauncher.setPort(port);
            sampLauncher.setPassword(pass);
            sampLauncher.connect();
        });
    }
}
