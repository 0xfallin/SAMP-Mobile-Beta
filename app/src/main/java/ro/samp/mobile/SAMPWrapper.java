package ro.samp.mobile;

public class SAMPWrapper {
    static {
        // Load GTASA first
        System.loadLibrary("GTASA");
        System.loadLibrary("SAMP"); // libsamp.so
    }

    // These functions must exist in your native .so
    public native void setNickname(String nickname);
    public native void setServer(String host, int port);
    public native void launchGame();
}
