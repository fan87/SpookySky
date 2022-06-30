package me.fan87.spookysky.injector;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
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

    @SneakyThrows
    public static void injectSharedObject(int pid, File file) {
        if (!file.exists()) {
            throw new Exception("File not found!");
        }
        if (!file.isFile()) {
            throw new Exception("File object is not an actual file!");
        }
        Process process = new ProcessBuilder().command("/home/fan87/Desktop/linux-inject/inject", "" + pid, file.getCanonicalPath())
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectInput(ProcessBuilder.Redirect.INHERIT)
                .start();
        process.waitFor();
    }

}
