package ro.samp.mobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    SAMPWrapper samp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        samp = new SAMPWrapper();

        EditText nickField = findViewById(R.id.nickname);
        EditText hostField = findViewById(R.id.host);
        EditText portField = findViewById(R.id.port);
        Button launchBtn = findViewById(R.id.launchBtn);

        launchBtn.setOnClickListener(v -> {
            String nick = nickField.getText().toString();
            String host = hostField.getText().toString();
            int port = Integer.parseInt(portField.getText().toString());

            saveSettings(nick, host, port);

            samp.launchGame();
        });
    }

    private void saveSettings(String nick, String host, int port) {
        try {
            JSONObject server = new JSONObject();
            server.put("host", host);
            server.put("port", port);
            server.put("password", "");

            JSONObject settings = new JSONObject();
            settings.put("nick_name", nick);
            settings.put("new_interface", false);
            settings.put("timestamp", false);
            settings.put("fast_connect", false);
            settings.put("voice_chat", false);
            settings.put("display_fps", false);
            settings.put("cleo", false);
            settings.put("fps_limit", 30);
            settings.put("chat_strings", 5);

            JSONObject client = new JSONObject();
            client.put("server", server);
            client.put("settings", settings);

            JSONObject root = new JSONObject();
            root.put("client", client);

            File sampFolder = new File(getExternalFilesDir(null), "SAMP");
            if (!sampFolder.exists()) sampFolder.mkdirs();

            File settingsFile = new File(sampFolder, "settings.json");
            try (FileWriter fw = new FileWriter(settingsFile)) {
                fw.write(root.toString(4));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
