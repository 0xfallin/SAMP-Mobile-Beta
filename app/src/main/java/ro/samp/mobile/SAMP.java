package ro.samp.mobile;

public class SAMP {

    private boolean loaded = false;

    // Native methods
    public native void startSAMP();
    public native void saveSettings(String host, int port, String nickname);

    // Load libraries manually
    public void loadLibraries() throws UnsatisfiedLinkError {
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
