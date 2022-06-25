package me.fan87.spookysky.injector;

public class NativeLinux {

    public static native void attach(int pid);
    public static native void detach(int pid);
    public static native void setRIP(int pid, long rip);
    public static native long getRIP(int pid);

}
