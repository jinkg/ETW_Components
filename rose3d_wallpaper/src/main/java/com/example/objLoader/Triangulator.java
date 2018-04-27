package com.example.objLoader;

import java.util.Vector;

public class Triangulator {
    public static Vector<Short> triangulate(Vector<Short> polygon) {
        Vector<Short> triangles = new Vector();
        for (int i = 1; i < polygon.size() - 1; i++) {
            triangles.add((Short) polygon.get(0));
            triangles.add((Short) polygon.get(i));
            triangles.add((Short) polygon.get(i + 1));
        }
        return triangles;
    }
}
