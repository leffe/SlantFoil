package com.tada.foil;

import java.util.ArrayList;
import java.util.List;

import com.sderhy.RawSpline;
import com.sderhy.RawSplineDouble;

public class BaseProfile {
    
    private double[] mXVals;
    
    private RawSplineDouble mSpline;

    private double mScale = 1.0f;

    public double getScale() {
        return mScale;
    }

    public void setScale(double f) {
        this.mScale = f;
    }
    
    private static double[] xToDouble(List<DoublePair> floatList, boolean getX) {
        double[] array = new double[floatList.size()];
        for (int i = 0; i < floatList.size(); i++) {
            Double f = getX ? floatList.get(i).getMa() : floatList.get(i).getMb() ;
            array[i] = (f != null ? f : Double.NaN); // Or whatever default
                                                            // you want.
        }
        return array;
    }
    
    protected RawSplineDouble createSpline(List<DoublePair> profilePairs) {
        double[] x = xToDouble(profilePairs, true);
        mXVals = x; // Save these for usable x-coordinates when creating new profiles.
        double[] z = xToDouble(profilePairs, false);
        return new RawSplineDouble(x, z, profilePairs.size());
    }

    public double getYAtRelX(double x) {
        return mSpline.CalcValue(x)*mScale;
    }

    public List<DoublePair> getPairs() {
        if (mSpline==null) return null; 
        List<DoublePair>  list = new ArrayList<DoublePair>();
        for (int idx = 0;idx < mXVals.length; idx++) {
            DoublePair fp = new DoublePair(mXVals[idx], mSpline.CalcValue(mXVals[idx]));
            list.add(fp);
        }
        return list;
    }
    
    public DoublePair getFirstPair() {
        return new DoublePair(mXVals[0], mSpline.CalcValue(mXVals[0]));
    }
    
    public DoublePair getLastPair() {
        int pos = mXVals.length-1;
        return new DoublePair(mXVals[pos], mSpline.CalcValue(mXVals[pos]));
    }
    
    protected RawSplineDouble getSpline() {
        return mSpline;
    }

    protected void setSpline(RawSplineDouble mSpline) {
        this.mSpline = mSpline;
    }

    public double [] getBaseXvals() {
        return mXVals;
    }
    
    public int getNbrOfXvals() {
        return mXVals.length;
    }
}
