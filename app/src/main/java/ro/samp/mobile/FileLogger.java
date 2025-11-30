package ro.samp.mobile;

import android.content.Context;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
    private File logFile;

    public FileLogger(Context context, String fileName) {
        logFile = new File(context.getFilesDir(), fileName);
    }

    public synchronized void log(String message) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(System.currentTimeMillis() + ": " + message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getLogFile() {
        return logFile;
    }
}
