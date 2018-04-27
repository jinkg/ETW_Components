package com.example.objLoader;

public class RGBColor {
    float f2B;
    float f3G;
    float f4R;

    public RGBColor(float r, float g, float b) {
        this.f4R = r;
        this.f3G = g;
        this.f2B = b;
    }

    public float getR() {
        return this.f4R;
    }

    public void setR(float r) {
        this.f4R = r;
    }

    public float getG() {
        return this.f3G;
    }

    public void setG(float g) {
        this.f3G = g;
    }

    public float getB() {
        return this.f2B;
    }

    public void setB(float b) {
        this.f2B = b;
    }

    public String toString() {
        return new StringBuilder(String.valueOf(new String())).append("R:").append(this.f4R).append(" G:").append(this.f3G).append(" B:").append(this.f2B).toString();
    }
}
