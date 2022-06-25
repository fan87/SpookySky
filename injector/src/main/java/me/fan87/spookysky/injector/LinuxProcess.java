package me.fan87.spookysky.injector;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.RandomAccessFile;

public class LinuxProcess {

    private final int pid;
    @Getter
    private RandomAccessFile randomAccessFile;

    @SneakyThrows
    public LinuxProcess(int pid) {
        this.pid = pid;
        NativeLinux.attach(pid);
        randomAccessFile = new RandomAccessFile("/proc/" + pid + "/mem", "rws");
    }


    public int getPid() {
        return pid;
    }

    @SneakyThrows
    public void close() {
        randomAccessFile.close();
        NativeLinux.detach(pid);
    }

    public void setRIP(long rip) {
        NativeLinux.setRIP(pid, rip);
    }
    public long getRIP() {
        return NativeLinux.getRIP(pid);
    }


}
