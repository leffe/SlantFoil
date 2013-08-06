package com.tada.foil;

public class Float3dPoint {

    private float mA;
    private float mB;
    private float mC;
    
    public Float3dPoint(float a, float b, float c) {
        mA = a;
        mB = b;
        mC = c;
    }
    
    public Float3dPoint(double a, double b, double c) {
        mA = new Double(a).floatValue();
        mB = new Double(b).floatValue();
        mC = new Double(c).floatValue();
    }

    public float getA() {
        return mA;
    }
    
    public float getB() {
        return mB;
    }

    public float getC() {
        return mC;
    }
}
