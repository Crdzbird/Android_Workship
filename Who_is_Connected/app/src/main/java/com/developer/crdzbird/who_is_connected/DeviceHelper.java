package com.developer.crdzbird.who_is_connected;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class DeviceHelper {

    private static final String TAG = "DeviceHelper";

    public static String getMac(String ip) {
        if (ip != null) {
            BufferedReader br = null;

            try {
                br = new BufferedReader(new FileReader("/proc/net/arp"));
            } catch (FileNotFoundException e) {
                Log.e(TAG, "file not found", e);
            }
            String line;
            try {
                if (br != null) {
                    while ((line = br.readLine()) != null) {
                        String[] splitted = line.split(" +");
                        if (splitted.length >= 4 && ip.equals(splitted[0])) {
                            String mac = splitted[3];
                            if (mac.matches("..:..:..:..:..:..")) {
                                return mac;
                            } else {
                                return null;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static ArrayList<String> getIps() {

        ArrayList<String> ips = new ArrayList<>();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/proc/net/arp"));
            } catch (FileNotFoundException e) {
                Log.e(TAG, "file not found", e);
            }
            String line;
            try {
                if (br != null) {
                    while ((line = br.readLine()) != null) {
                        String[] splitted = line.split(" +");
                        if (splitted.length >= 4 && ((splitted[2].equals("0x2")) || (splitted[2].equals("0x0")))) {
                            String mac = splitted[3];
                            if (!mac.matches("00:00:00:00:00:00")) {
                                ips.add(splitted[0]);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        Collections.sort(ips);
        return ips;
    }


}
