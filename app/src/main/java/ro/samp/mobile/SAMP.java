package ro.samp.mobile;

public class SAMP {

    private static boolean loaded = false;

    public native void startSAMP();
    public native void saveSettings(String host, int port, String nickname);
    public native void setAppStoragePath(String path); // NEW

    public static void loadLibraries(String appPath) {
        if (!loaded) {
            System.loadLibrary("GTASA");

            // pass the app path to native code
            new SAMP().setAppStoragePath(appPath);

            System.loadLibrary("samp");
            loaded = true;
        }
    }
}
