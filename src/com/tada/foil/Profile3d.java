package com.tada.foil;

import java.util.ArrayList;
import java.util.List;

public class Profile3d {

    private BaseProfile mProfile;
    private Float3dPoint mPoint;
    private double mAngleRad; // In radians
    private double mTteThickness = 0f;  
    private double mTeStartAt = 0f;
    
    public Profile3d (BaseProfile profile, Float3dPoint point, double angleDeg) {
        mProfile = profile;
        mPoint= point;
        mAngleRad = angleDeg * Math.PI / 180;
    }
    
    /**
     * Set thickness of trailing edge
     * @param teThickness - Thickness in absolute terms
     * @param teStartAt - Start to thicken at percent of cord. 
     */
    public void setThickening(double teThickness, double teStartAt) {
        mTteThickness = teThickness;
        mTeStartAt = teStartAt;
    }
    
    /**
     * Returns the profile in the 3d coordinate system. Profile is scaled and 
     * positioned and slanted according to angle. 
     */
    public List<Float3dPoint> getPoints() {
        double scale = mProfile.getScale();
        boolean teThickening = mTeStartAt < 0.99f;
        List<DoublePair> pairs2d = mProfile.getPairs();
        List<Float3dPoint> points3d = new ArrayList<Float3dPoint>();
        for (DoublePair pair : pairs2d) {
            DoublePair fp = pair;
            if (teThickening && pair.getMa() > mTeStartAt) {
                double d = (pair.getMa() - mTeStartAt) / (1f - mTeStartAt);
                fp = new DoublePair(pair.getMa(), pair.getMb() + d * mTteThickness/scale);
            }
            double x = mPoint.getA() + fp.getMa() * Math.cos(mAngleRad) * scale;
            double y = mPoint.getB() + fp.getMa() * Math.sin(mAngleRad) * scale;
            double z = fp.getMb() * scale;
            points3d.add(new Float3dPoint(x, y, z));
        }
//      if (teThickening && fp.getMa() > teStartAt) {
//      float d = (fp.getMa() - teStartAt) / (1f - teStartAt);
//      fp = new FloatPair(fp.getMa(), fp.getMb() + d * teThickness/scale);
//  }
        
        return points3d;
    }
}
