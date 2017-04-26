package com.app.hos.hosclient.utility;

import android.app.ActivityManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static android.content.Context.ACTIVITY_SERVICE;


public class Usage {

    private static Usage instance = null;
    private BufferedReader reader;

    protected Usage() {}

    public static Usage getInstance() {
        if(instance == null) {
            instance = new Usage();
        }
        return instance;
    }

    public double getCpuUsage() {
        double cpuResult;
        try {
            reader = new BufferedReader(new FileReader("/proc/stat"));
            String[] sa = reader.readLine().split("[ ]+");
            long user =         Long.parseLong(sa[1]);
            long nice =         Long.parseLong(sa[2]);
            long system =       Long.parseLong(sa[3]);
            long idle =         Long.parseLong(sa[4]);
            long iowait =       Long.parseLong(sa[5]);
            long irq =          Long.parseLong(sa[6]);
            long softirq =      Long.parseLong(sa[7]);
            long total = user + nice + system + idle + iowait + irq + softirq;
            double cpu = (idle * 100.0) / total;
            cpuResult = 100 - cpu;
        } catch (Exception e) {
            cpuResult = -1;
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return cpuResult;
    }

    public double getRamUsage() {
        double ramResult;
        String line;
        int memTotal = 0;
        int memFree = 0;
        try {
            reader = new BufferedReader(new FileReader("/proc/meminfo"));
            line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                if (line.startsWith("MemTotal:"))
                    memTotal = Integer.parseInt(line.split("[ ]+", 3)[1]);
                if (line.startsWith("MemFree:")) {
                    memFree = Integer.parseInt(line.split("[ ]+", 3)[1]);
                    break;
                }
                line = reader.readLine();
            }
            ramResult = memFree  * 100 / memTotal;
            reader.close();
        } catch (Exception e) {
            ramResult = -1;
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return ramResult;
    }
}
