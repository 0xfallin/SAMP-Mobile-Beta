package ro.samp.mobile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView userName = findViewById(R.id.userName);
        TextView serverIpPort = findViewById(R.id.serverIpPort);
        Button connectButton = findViewById(R.id.connectButton);

        // Set server info from ServerConfig
        userName.setText(ServerConfig.USER_NAME);
        serverIpPort.setText(ServerConfig.SERVER_IP + ":" + ServerConfig.SERVER_PORT);

        connectButton.setOnClickListener(v -> {
            // For now, just show a toast
            Toast.makeText(this,
                    "Connecting to " + ServerConfig.SERVER_IP + ":" + ServerConfig.SERVER_PORT,
                    Toast.LENGTH_SHORT).show();

            // TODO: Integrate SAMP connection logic here
        });
    }
}
