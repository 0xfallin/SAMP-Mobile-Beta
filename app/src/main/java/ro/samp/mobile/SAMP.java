package ro.samp.mobile;

public class SAMP {

    private static boolean loaded = false;

    public native void startSAMP();
    public native void stopSAMP();
    public native void setStoragePath(String path);

    public static void loadLibraries(String storagePath) {
        if (loaded) return;

        System.loadLibrary("GTASA");
        System.loadLibrary("bass");
        System.loadLibrary("ImmEmulator");
        System.loadLibrary("touch");
        System.loadLibrary("SCAnd");
        System.loadLibrary("samp");

        new SAMP().setStoragePath(storagePath);

        loaded = true;
    }
}
