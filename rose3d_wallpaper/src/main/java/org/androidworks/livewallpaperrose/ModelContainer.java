package org.androidworks.livewallpaperrose;

import android.content.Context;

import com.example.objLoader.TDModel;
import com.example.objLoader.TDModelPart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class ModelContainer {
    private static final int FLOAT_SIZE_BYTES = 4;
    private int numPolysModel;
    private FloatBuffer triangleVerticesModel;

    public ModelContainer(TDModel model) {
        this.triangleVerticesModel = loadModel(model);
        this.numPolysModel = this.triangleVerticesModel.capacity() / 5;
    }

    public ModelContainer(Context context, String fileName) {
        InputStream fis = null;
        try {
            fis = context.getAssets().open(fileName);
        } catch (IOException e) {
        }
        byte[] data = readFile(fis);
        ByteBuffer result = ByteBuffer.allocateDirect(data.length).order(ByteOrder.LITTLE_ENDIAN);
        result.put(data).position(0);
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e2) {
            }
        }
        this.triangleVerticesModel = result.asFloatBuffer();
        this.numPolysModel = this.triangleVerticesModel.capacity() / 5;
    }

    private byte[] readFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (true) {
            try {
                int len = inputStream.read(buf);
                if (len == -1) {
                    break;
                }
                outputStream.write(buf, 0, len);
            } catch (IOException e) {
            }
        }
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    public ModelContainer(TDModel model, Boolean bLoadNormals) {
        if (bLoadNormals.booleanValue()) {
            this.triangleVerticesModel = loadModelWithNormals(model);
            this.numPolysModel = this.triangleVerticesModel.capacity() / 8;
            return;
        }
        this.triangleVerticesModel = loadModel(model);
        this.numPolysModel = this.triangleVerticesModel.capacity() / 5;
    }

    protected FloatBuffer loadModel(TDModel model) {
        TDModelPart part = (TDModelPart) model.parts.get(0);
        float[] triangleVerticesData = new float[(part.getFacesCount() * 5)];
        for (int i = 0; i < part.getFacesCount(); i++) {
            int p = i * 5;
            triangleVerticesData[p + 0] = ((Float) model.f5v.get((((Short) part.faces.get(i)).shortValue() * 3) + 0)).floatValue();
            triangleVerticesData[p + 1] = ((Float) model.f5v.get((((Short) part.faces.get(i)).shortValue() * 3) + 1)).floatValue();
            triangleVerticesData[p + 2] = ((Float) model.f5v.get((((Short) part.faces.get(i)).shortValue() * 3) + 2)).floatValue();
            triangleVerticesData[p + 3] = ((Float) model.vt.get((((Short) part.vtPointer.get(i)).shortValue() * 3) + 0)).floatValue();
            triangleVerticesData[p + FLOAT_SIZE_BYTES] = 1.0f - ((Float) model.vt.get((((Short) part.vtPointer.get(i)).shortValue() * 3) + 1)).floatValue();
        }
        FloatBuffer result = ByteBuffer.allocateDirect(triangleVerticesData.length * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(triangleVerticesData).position(0);
        return result;
    }

    protected FloatBuffer loadModelWithNormals(TDModel model) {
        TDModelPart part = (TDModelPart) model.parts.get(0);
        float[] triangleVerticesData = new float[(part.getFacesCount() * 8)];
        for (int i = 0; i < part.getFacesCount(); i++) {
            int p = i * 8;
            triangleVerticesData[p + 0] = ((Float) model.f5v.get((((Short) part.faces.get(i)).shortValue() * 3) + 0)).floatValue();
            triangleVerticesData[p + 1] = ((Float) model.f5v.get((((Short) part.faces.get(i)).shortValue() * 3) + 1)).floatValue();
            triangleVerticesData[p + 2] = ((Float) model.f5v.get((((Short) part.faces.get(i)).shortValue() * 3) + 2)).floatValue();
            triangleVerticesData[p + 3] = ((Float) model.vt.get((((Short) part.vtPointer.get(i)).shortValue() * 3) + 0)).floatValue();
            triangleVerticesData[p + FLOAT_SIZE_BYTES] = 1.0f - ((Float) model.vt.get((((Short) part.vtPointer.get(i)).shortValue() * 3) + 1)).floatValue();
            triangleVerticesData[p + 5] = ((Float) model.vn.get((((Short) part.faces.get(i)).shortValue() * 3) + 0)).floatValue();
            triangleVerticesData[p + 6] = ((Float) model.vn.get((((Short) part.faces.get(i)).shortValue() * 3) + 1)).floatValue();
            triangleVerticesData[p + 7] = ((Float) model.vn.get((((Short) part.faces.get(i)).shortValue() * 3) + 2)).floatValue();
        }
        FloatBuffer result = ByteBuffer.allocateDirect(triangleVerticesData.length * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(triangleVerticesData).position(0);
        return result;
    }

    public int getNumPolys() {
        return this.numPolysModel;
    }

    public FloatBuffer getBuffer() {
        return this.triangleVerticesModel;
    }
}
