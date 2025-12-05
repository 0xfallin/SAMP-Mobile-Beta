package ro.samp.mobile;

public class SAMP {

    private static boolean loaded = false;

    // Load libraries safely
    public static void loadLibraries() {
        if (!loaded) {
            try {
                System.loadLibrary("GTASA"); // dependency
                System.loadLibrary("samp");
                loaded = true;
            } catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
            }
        }
    }

    // Native methods
    public native void startSAMP();
    public native void saveSettings(String host, int port, String nickname);

    // Optional: convenience method
    public void startWithSettings(String host, int port, String nickname) {
        saveSettings(host, port, nickname);
        startSAMP();
    }
}
