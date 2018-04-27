package com.example.objLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Material {
    float alpha;
    float[] ambientColor;
    float[] diffuseColor;
    int illum;
    String name;
    float shine;
    float[] specularColor;
    String textureFile;

    public Material(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float[] getAmbientColor() {
        return this.ambientColor;
    }

    public FloatBuffer getAmbientColorBuffer() {
        ByteBuffer b = ByteBuffer.allocateDirect(12);
        b.order(ByteOrder.nativeOrder());
        FloatBuffer f = b.asFloatBuffer();
        f.put(this.ambientColor);
        f.position(0);
        return f;
    }

    public void setAmbientColor(float r, float g, float b) {
        this.ambientColor = new float[3];
        this.ambientColor[0] = r;
        this.ambientColor[1] = g;
        this.ambientColor[2] = b;
    }

    public float[] getDiffuseColor() {
        return this.diffuseColor;
    }

    public FloatBuffer getDiffuseColorBuffer() {
        ByteBuffer b = ByteBuffer.allocateDirect(12);
        b.order(ByteOrder.nativeOrder());
        FloatBuffer f = b.asFloatBuffer();
        f.put(this.diffuseColor);
        f.position(0);
        return f;
    }

    public void setDiffuseColor(float r, float g, float b) {
        this.diffuseColor = new float[3];
        this.diffuseColor[0] = r;
        this.diffuseColor[1] = g;
        this.diffuseColor[2] = b;
    }

    public float[] getSpecularColor() {
        return this.specularColor;
    }

    public FloatBuffer getSpecularColorBuffer() {
        ByteBuffer b = ByteBuffer.allocateDirect(12);
        b.order(ByteOrder.nativeOrder());
        FloatBuffer f = b.asFloatBuffer();
        f.put(this.specularColor);
        f.position(0);
        return f;
    }

    public void setSpecularColor(float r, float g, float b) {
        this.specularColor = new float[3];
        this.specularColor[0] = r;
        this.specularColor[1] = g;
        this.specularColor[2] = b;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getShine() {
        return this.shine;
    }

    public void setShine(float shine) {
        this.shine = shine;
    }

    public int getIllum() {
        return this.illum;
    }

    public void setIllum(int illum) {
        this.illum = illum;
    }

    public String getTextureFile() {
        return this.textureFile;
    }

    public void setTextureFile(String textureFile) {
        this.textureFile = textureFile;
    }

    public String toString() {
        return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new String())).append("Material name: ").append(this.name).toString())).append("\nAmbient color: ").append(this.ambientColor.toString()).toString())).append("\nDiffuse color: ").append(this.diffuseColor.toString()).toString())).append("\nSpecular color: ").append(this.specularColor.toString()).toString())).append("\nAlpha: ").append(this.alpha).toString())).append("\nShine: ").append(this.shine).toString();
    }
}
