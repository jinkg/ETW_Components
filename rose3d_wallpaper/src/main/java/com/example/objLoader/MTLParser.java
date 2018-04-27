package com.example.objLoader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class MTLParser {
    public static Vector<Material> loadMTL(String file) {
        BufferedReader reader = null;
        Vector<Material> materials = new Vector();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (IOException e) {
        }
        if (reader != null) {
            Material material;
            Material currentMtl = null;
            while (true) {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    } else if (line.startsWith("newmtl")) {
                        if (currentMtl != null) {
                            materials.add(currentMtl);
                        }
                        currentMtl = new Material(line.split("[ ]+", 2)[1]);
                    } else if (line.startsWith("Ka")) {
                        String[] str = line.split("[ ]+");
                        currentMtl.setAmbientColor(Float.parseFloat(str[1]), Float.parseFloat(str[2]), Float.parseFloat(str[3]));
                    } else if (line.startsWith("Kd")) {
                        String[] str = line.split("[ ]+");
                        currentMtl.setDiffuseColor(Float.parseFloat(str[1]), Float.parseFloat(str[2]), Float.parseFloat(str[3]));
                    } else if (line.startsWith("Ks")) {
                        String[] str = line.split("[ ]+");
                        currentMtl.setSpecularColor(Float.parseFloat(str[1]), Float.parseFloat(str[2]), Float.parseFloat(str[3]));
                    } else if (line.startsWith("Tr") || line.startsWith("d")) {
                        currentMtl.setAlpha(Float.parseFloat(line.split("[ ]+")[1]));
                    } else if (line.startsWith("Ns")) {
                        currentMtl.setShine(Float.parseFloat(line.split("[ ]+")[1]));
                    } else if (line.startsWith("illum")) {
                        currentMtl.setIllum(Integer.parseInt(line.split("[ ]+")[1]));
                    } else if (line.startsWith("map_Ka")) {
                        currentMtl.setTextureFile(line.split("[ ]+")[1]);
                    }
                } catch (Exception e2) {
                    material = currentMtl;
                }
            }
            material = currentMtl;
        }
        return materials;
    }
}
