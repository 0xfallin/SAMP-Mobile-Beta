package ro.samp.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SampLauncher launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launcher = new SampLauncher();

        Button btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run SAMP in a separate thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        launcher.initSAMP(getFilesDir().getAbsolutePath());
                        launcher.startGameLoop();
                    }
                }).start();
            }
        });
    }
}
