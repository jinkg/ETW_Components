package com.example.objLoader;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Pattern;

public class OBJParser {
    Context context;
    Vector<Short> faces = new Vector();
    Vector<Material> materials = null;
    int numFaces = 0;
    int numVertices = 0;
    Vector<TDModelPart> parts = new Vector();
    private Pattern patternFLine;
    private Pattern patternSplit;
    Vector<Float> f1v = new Vector();
    Vector<Float> vn = new Vector();
    Vector<Short> vnPointer = new Vector();
    Vector<Float> vt = new Vector();
    Vector<Short> vtPointer = new Vector();

    public OBJParser(Context ctx) {
        this.context = ctx;
        this.patternFLine = Pattern.compile("[0-9]+/[0-9]+/[0-9]+");
        this.patternSplit = Pattern.compile("[ ]+");
    }

    public TDModel parseOBJ(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.context.getAssets().open(fileName)));

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("f")) {
                    processFLine(line);
                } else if (line.startsWith("vn")) {
                    processVNLine(line);
                } else if (line.startsWith("vt")) {
                    processVTLine(line);
                } else if (line.startsWith("v")) {
                    processVLine(line);
                }
            }
        } catch (IOException e) {
        }
        if (this.faces != null) {
            this.parts.add(new TDModelPart(this.faces, this.vtPointer, this.vnPointer, null, this.vn));
        }
        TDModel t = new TDModel(this.f1v, this.vn, this.vt, this.parts);
        t.buildVertexBuffer();
        return t;
    }

    private void processVLine(String line) {
        String[] tokens = this.patternSplit.split(line);
        int c = tokens.length;
        for (int i = 1; i < c; i++) {
            this.f1v.add(Float.valueOf(tokens[i]));
        }
    }

    private void processVNLine(String line) {
        String[] tokens = this.patternSplit.split(line);
        int c = tokens.length;
        for (int i = 1; i < c; i++) {
            this.vn.add(Float.valueOf(tokens[i]));
        }
    }

    private void processVTLine(String line) {
        String[] tokens = this.patternSplit.split(line);
        int c = tokens.length;
        for (int i = 1; i < c; i++) {
            this.vt.add(Float.valueOf(tokens[i]));
        }
    }

    private void processFLine(String line) {
        String[] tokens = this.patternSplit.split(line);
        int c = tokens.length;
        if (!this.patternFLine.matcher(tokens[1]).matches()) {
            return;
        }
        int i;
        if (c == 4) {
            for (i = 1; i < c; i++) {
                this.faces.add(Short.valueOf((short) (Short.valueOf(tokens[i].split("/")[0]).shortValue() - 1)));
                this.vtPointer.add(Short.valueOf((short) (Short.valueOf(tokens[i].split("/")[1]).shortValue() - 1)));
                this.vnPointer.add(Short.valueOf((short) (Short.valueOf(tokens[i].split("/")[2]).shortValue() - 1)));
            }
            return;
        }
        Vector<Short> tmpFaces = new Vector();
        Vector<Short> tmpVn = new Vector();
        Vector<Short> tmpVt = new Vector();
        for (i = 1; i < tokens.length; i++) {
            tmpFaces.add(Short.valueOf((short) (Short.valueOf(tokens[i].split("/")[0]).shortValue() - 1)));
            tmpVt.add(Short.valueOf((short) (Short.valueOf(tokens[i].split("/")[1]).shortValue() - 1)));
            tmpVn.add(Short.valueOf((short) (Short.valueOf(tokens[i].split("/")[2]).shortValue() - 1)));
        }
        this.faces.addAll(Triangulator.triangulate(tmpFaces));
        this.vtPointer.addAll(Triangulator.triangulate(tmpVt));
        this.vnPointer.addAll(Triangulator.triangulate(tmpVn));
    }
}
