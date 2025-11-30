package ro.samp.mobile;

public class SAMPLauncher {
    static {
        System.loadLibrary("libsamp"); // your compiled .so
    }

    public native void setNick(String nick);
    public native void setServerIP(String ip);
    public native void setPort(int port);
    public native void setPassword(String password);
    public native void connect();
}
