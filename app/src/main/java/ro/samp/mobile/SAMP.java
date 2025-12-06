package ro.samp.mobile;

public class SAMP {
    private static boolean loaded = false;

    public static void loadLibraries() {
        if (loaded) return;

        // Load native library
        System.loadLibrary("samp");

        // No need to set storage path anymore, it's hardcoded in native
        // new SAMP().setStoragePath(storagePath);  <-- remove

        loaded = true;
    }

    // JNI methods
    public native void startSAMP();
    public native void stopSAMP();
    public native void initNative();
}
