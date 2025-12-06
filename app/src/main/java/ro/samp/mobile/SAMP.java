package ro.samp.mobile;

public class SAMP {
    private static boolean loaded = false;
    public static void loadLibraries(String storagePath) {
        if (loaded) return;

        System.loadLibrary("samp");

        new SAMP().setStoragePath(storagePath);

        loaded = true;
    }

    public native void startSAMP();
    public native void stopSAMP();
    public native void setStoragePath(String path);
    public native void initNative();
}
