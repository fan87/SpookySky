package me.fan87.spookysky.injector;

import com.mageddo.jvm.attach.JvmAttach;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.Scanner;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
//        System.loadLibrary("hello");
        int pid = 849520;
        System.out.println("Running as user: " + System.getProperty("user.name"));
        System.out.println("Injecting to PID: " + pid);
        LinuxProcess.injectSharedObject(pid, new File("/home/fan87/Desktop/linux-inject/spookysky.so"));
    }

}
