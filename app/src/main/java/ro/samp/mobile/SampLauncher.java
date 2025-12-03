package ro.samp.mobile;

public class SampLauncher {

    static {
        System.loadLibrary("GTASA"); // your native .so
        System.loadLibrary("samp"); // your native .so
    }

    // Expose native methods
    public native void initSAMP(String storagePath);
    public native void startGameLoop();
    public native void quitGame();
}
