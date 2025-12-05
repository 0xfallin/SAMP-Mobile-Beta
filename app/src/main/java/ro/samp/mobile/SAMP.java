package ro.samp.mobile;

public class SAMP {
    static {
        System.loadLibrary("samp"); // your native library
    }

    public native void startSAMP();
    public native void stopSAMP(); // optional, to stop SAMP if needed
}
