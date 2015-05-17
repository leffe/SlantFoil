package com.tada.slantfoil;

import com.tada.foil.BaseProfile;
import com.tada.foil.Float3dPoint;
import com.tada.foil.WingSurface;

public class RibSet {
    private WingSurface mSurface; 
    public  double mPosition;
    public double mAngle;
    
    public RibSet(WingSurface surface, double position, double angle) {
        mSurface = surface;
        mPosition = position;
        mAngle = angle;
    }
    
    public BaseProfile getProfile() {
      return mSurface.getProfileAt(mPosition, mAngle);
    }
    
    public Float3dPoint getProfileOrigo() {
        double x = mPosition;
        Double z = mSurface.getPx0At(mPosition);
        double y = 0f; // Not implemented yet
        return new Float3dPoint(x, y, z.floatValue());
    }
    
    public WingSurface getSurface() {
        return mSurface;
    } 
}
