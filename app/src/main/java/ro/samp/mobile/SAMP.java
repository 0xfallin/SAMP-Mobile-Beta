package ro.samp.mobile;

public class SAMP {

    private static boolean loaded = false;

    public native void startSAMP();
    public native void saveSettings(String host, int port, String nickname);
    public native void setAppStoragePath(String path); // NEW

    public static void loadLibraries(String appPath) {
        if (!loaded) {
            // pass the app path to native code
            System.loadLibrary("samp");
            new SAMP().setAppStoragePath(appPath);
            loaded = true;
        }
    }
}
