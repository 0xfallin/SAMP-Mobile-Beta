package ro.samp.mobile;

public class SAMP {

    private static boolean loaded = false;

    // Native methods
    public native void startSAMP();
    public native void saveSettings(String host, int port, String nickname);

    // Load libraries manually (static now)
    public static void loadLibraries() throws UnsatisfiedLinkError {
        if (!loaded) {
            System.loadLibrary("GTASA"); // load first
            System.loadLibrary("samp");  // load second
            loaded = true;
        }
    }

    // Convenience method
    public void startWithSettings(String host, int port, String nickname) {
        saveSettings(host, port, nickname);
        startSAMP();
    }
}
