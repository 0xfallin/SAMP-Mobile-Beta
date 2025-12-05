package ro.samp.mobile;

public class SAMP {

    static {
        try {
            System.loadLibrary("GTASA");
            System.loadLibrary("samp");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    // Native methods
    public native void startSAMP();
    public native void saveSettings(String host, int port, String nickname);

    // Optional: convenience method to save and start SAMP
    public void startWithSettings(String host, int port, String nickname) {
        saveSettings(host, port, nickname);
        startSAMP();
    }
}
