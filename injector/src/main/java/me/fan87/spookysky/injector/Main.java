package me.fan87.spookysky.injector;

import com.mageddo.jvm.attach.JvmAttach;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("Running as user: " + System.getProperty("user.name"));
        System.loadLibrary("hello");
        int pid = 987420;
        LinuxProcess process = new LinuxProcess(pid);
        try {
            long rip = process.getRIP();
            System.out.println("Old RIP: " + rip);
            process.setRIP(rip);
            System.out.println("New RIP: " + process.getRIP());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            process.close();
        }
    }

}
