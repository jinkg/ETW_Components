package com.xmodpp.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Network {
    private static String HTTPGet(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            String stringBuilder;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder stringBuilder2 = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder2.append(readLine);
                    stringBuilder2.append('\n');
                }
                stringBuilder = stringBuilder2.toString();
                return stringBuilder;
            } catch (Exception e) {
                stringBuilder = "Exception: " + e;
                return stringBuilder;
            } finally {
                httpURLConnection.disconnect();
            }
        } catch (Exception e2) {
            return "Exception1: " + e2.getLocalizedMessage();
        }
    }
}
