package com.tada.foil;

import java.util.ArrayList;
import java.util.List;

import com.sderhy.RawSpline;
import com.tada.foil.Util;
/**
 * Describes the surface of e.g. a wing, top or bottom surface.
 * The surface is described in the x and z plane. y is the height of the plane.
 * 
 * @author Leif Sten
 *
 */
public class WingSurface {
	
	EdgeProfile mRoot;
	EdgeProfile mTip;
	double mSweep = 0.0d;
	double mSpan = 1.0d;
	
	// Trailing edge thickening in scale
	double mTeThickness;
	// Start at percent of cord, 0 at LE, 100 at TE
	double mTeStartAt;
	
	public WingSurface(EdgeProfile rootEdgeUpper, EdgeProfile tipEdgeUpper) {
	    mRoot = rootEdgeUpper;
	    mTip = tipEdgeUpper;
    }

	/**
	 * 
	 * @param z
	 * @param alpha in degrees.
	 * @return
	 */
    public BaseProfile getProfileAt(double z, double alpha) {


        double alpaRad = alpha*Math.PI/180;
        double k0 = Math.tan(alpaRad);
        double m0 = getPx0At(z)-k0*z;
        
        double k1 = getDxDz1();
        double m1 = mRoot.getScale();
        
        double zP1 = (m1-m0)/(k0-k1);
        
//        System.out.println("k0, m0:" + k0 + "," +m0);
//        System.out.println("k1, m1:" + k1 + "," +m1);
        
        System.out.println("zp:" + zP1);
        
        double aDz = zP1-z;
        double aDx = zP1*k1+m1-getPx0At(z);
        
        // Target cord 
        double tCord = Math.sqrt(Math.pow(aDz,2f)+Math.pow(aDx,2f));      
        
        double[] baseXvals = mRoot.getBaseXvals();
 
        double cosAlpha = Math.cos(alpaRad);
        double sinAlpha = Math.sin(alpaRad);
        
        List<DoublePair> newFoil = new ArrayList<DoublePair>();
        
        for (int idx = 0; idx < baseXvals.length; idx++) {
            double tx = baseXvals[idx]*tCord;
            double zp = z + tx * cosAlpha;
            double xp = getPx0At(z) + tx * sinAlpha;
            double yp = getYAtPz(xp, zp);
//                      System.out.println("idx,bv,tx,zp,xp,yp: "+idx+", "+
//                              Util.f(baseXvals[idx])+", "+ Util.f(tx)+
//                              ", "+Util.f(zp)+", " + Util.f(xp) + ", " +yp);

            double x0 = getPx0At(zp);          
                      
            DoublePair fp = new DoublePair(baseXvals[idx], toDouble(yp/tCord));
            newFoil.add(fp);
            
        }
        
        BaseProfile bp = new BaseProfile();
        bp.setSpline(bp.createSpline(newFoil));
        bp.setScale(toDouble(tCord));
        
//        bp.getSpline().show();
//        float a0 = getPx0At(z);
//        float a1 = getCordaAt(z)+a0;
        
        return bp;
    }

    public double toDouble(double f){
        return (new Double(f)).floatValue();
    }
    
    
    /**
     * Get y for the profile parallel to the x-axis at x,z.
     * @param xp Absolute x value
     * @param zp Absolute z value
     * @return Absolute y in position x,z 
     */
    private double getYAtPz(double xp, double zp) {
        // Calculate relative x position
        double x0AtZ = getPx0At(zp);
        double cordaAtZ = getCordAt(zp);
        double relX = (xp-x0AtZ)/cordaAtZ;
        // y values at edges
//        double relXf = (new Double(relX)).floatValue();
        double yAtRoot = mRoot.getYAtRelX(relX);
        double yAtTip = mTip.getYAtRelX(relX);
        // Final y at position
        double yRel = yAtRoot - (yAtRoot-yAtTip)*(zp/mSpan);
        return yRel;
    }
    
    public double getCordAt(double pZ) {
        double root = mRoot.getScale();
        double ret = root + getPx1PxAt(pZ) - getPx0At(pZ);
        return ret;
    }
    
    public double getPx0At(double pZ) {
        double res = (getTip0())*pZ/mSpan;
        return res;
    }
    
    private double getPx1PxAt(double pZ) {
        double root = mRoot.getScale();
        double res = (getTip1()-root)*pZ/mSpan;
        return res;
    }
    
    private double getTip0() {
        double root = mRoot.getScale();
        double tip = mTip.getScale();
        double ret = root/2.0f-mSweep-tip/2;
        return ret;
    }
    
    private double getTip1() {
        double tip = mTip.getScale();
        double ret = getTip0()+tip;
        return ret;
    }
    
    private double getDxDz0() {
        double ret = getTip0()/mSpan;
        return ret;
    }
    
    private double getDxDz1() {
        double ret = (getTip1()-mRoot.getScale())/mSpan;
        return ret;
    }
    
    
    
    /*
     * Getters and setters
     */
    public double getSweep() {
        return mSweep;
    }

    public double getmTeThickness() {
        return mTeThickness;
    }

    public double getmTeStartAt() {
        return mTeStartAt;
    }

    public void setSweep(double s) {
        this.mSweep = s;
    }

    public double getSpan() {
        return mSpan;
    }

    public void setSpan(double f) {
        this.mSpan = f;
    }

    public void setRootScale(double f) {
        mRoot.setScale(f);
    }

    public void setTipScale(double f) {
        mTip.setScale(f);
    }

    public void test() {

        double i;
        i = 0f;
        System.out.println("corda["+i+"]:" + getCordAt(i));
        System.out.println("Px0["+i+"]:" + getPx0At(i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i), i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i)/2, i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i)*8/10, i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i), i));
       i = mSpan;
        System.out.println("corda["+i+"]:" + getCordAt(i));
        System.out.println("Px0["+i+"]:" + getPx0At(i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i), i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i)/2, i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i)*8/10, i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i), i));
        
        
        
        i = mSpan*2/3;
        System.out.println("corda["+i+"]:" + getCordAt(i));
        System.out.println("Px0["+i+"]:" + getPx0At(i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i), i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i)/2, i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i)*8/10, i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i), i));
        i = mSpan*1/3;
        System.out.println("corda["+i+"]:" + getCordAt(i));
        System.out.println("Px0["+i+"]:" + getPx0At(i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i), i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i)/2, i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i)*8/10, i));
        System.out.println("yAt["+i+"]:" + getYAtPz(getPx0At(i)+getCordAt(i), i));
    }

    public void setTrailingEdge(double thickness, double startAt) {
        mTeThickness = thickness;
        mTeStartAt = startAt;
    }

    public void copyParams(WingSurface src) {
        mSpan = src.mSpan;
        mSweep = src.mSweep;
        mTeStartAt = src.mTeStartAt;
        mTeThickness = src.mTeThickness;
        setRootScale(src.mRoot.getScale());
        setTipScale(src.mTip.getScale());
    }

}
