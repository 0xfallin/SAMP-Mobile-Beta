package ro.samp.mobile;

public class SAMP {

    private static boolean loaded = false;

    public native void startSAMP();
    public native void saveSettings(String host, int port, String nickname);

    public static void loadLibraries() {
        if (!loaded) {
            System.loadLibrary("GTASA");
            System.loadLibrary("samp");
            loaded = true;
        }
    }
}
