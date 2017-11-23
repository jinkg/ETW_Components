package com.xmodpp.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Network {
    private static String HTTPGet(String url) {
        String stringBuilder;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder total = new StringBuilder();
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    total.append(line);
                    total.append('\n');
                }
                stringBuilder = total.toString();
                return stringBuilder;
            } catch (Exception e) {
                stringBuilder = "Exception: " + e;
                return stringBuilder;
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e1) {
            return "Exception1: " + e1.getLocalizedMessage();
        }
    }
}
