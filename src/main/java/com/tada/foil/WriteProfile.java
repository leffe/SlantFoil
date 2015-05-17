package com.tada.foil;

import static com.tada.foil.Util.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.ListIterator;

import com.tada.slantfoil.RibSet;

public class WriteProfile {


    public static void write(BaseProfile profile, double scale, OutputStreamWriter os) throws IOException {
        writeLocal(profile, scale, 0f, 1f, os);
    }
    
    /**
     * 
     * @param profile
     * @param scale
     * @param teThickness Thickening trailing edge in scale
     * @param teStartAt Thickening of TE starts at percent, o at LE and 100 at TE.
     * @param os
     * @throws IOException
     */
    private static void writeLocal(BaseProfile profile, double scale, 
            double teThickness, double teStartAt, OutputStreamWriter os) throws IOException {
        
        boolean teThickening = teStartAt < 0.99f;
        
        List<DoublePair> list = profile.getPairs();
        ListIterator<DoublePair> li = list.listIterator();
        DoublePair fp;
        while (li.hasNext()) {
            fp = li.next();
            if (teThickening && fp.getMa() > teStartAt) {
                double d = (fp.getMa() - teStartAt) / (1f - teStartAt);
                fp = new DoublePair(fp.getMa(), fp.getMb() + d * teThickness/scale);
            }
            System.out.println(f(fp.getMa())+", "+f(fp.getMb()));
            os.write(f(fp.getMa()*scale)+","+f(fp.getMb()*scale)+ NL);
        }
    }
    
    private static void writeLocal3d(RibSet r0, OutputStreamWriter os) throws IOException {
        Profile3d profile3d = new Profile3d(r0.getProfile(), r0.getProfileOrigo(), r0.mAngle);
        profile3d.setThickening(r0.getSurface().mTeThickness, r0.getSurface().mTeStartAt);
        List<Float3dPoint> list = profile3d.getPoints();
        ListIterator<Float3dPoint> li = list.listIterator();
        Float3dPoint fp;
        while (li.hasNext()) {
            fp = li.next();
            os.write(f(fp.getA())+","+f(fp.getB())+","+f(fp.getC())+ NL);
        }
    }
    
    
    public static void writeAutocad(BaseProfile profile, double teThickness, double teStartAt, 
            OutputStreamWriter os) throws IOException {
        double scale = profile.getScale();
        os.write("LIMITS OFF"+NL);
        os.write("PLINE"+NL);
        writeLocal(profile, scale, teThickness, teStartAt, os);
        os.write(NL);
        os.write("LINE"+NL);
        DoublePair fp = profile.getFirstPair();
        os.write(f(fp.getMa()*scale)+","+f(fp.getMb()*scale)+NL);
        fp = profile.getLastPair();
        os.write(f(fp.getMa()*scale)+","+f(fp.getMb()*scale)+NL);
        os.write(NL);
    }    
    
    public static void writeAutocadMesh(RibSet r0, RibSet r1, WingSurface surface, 
            OutputStreamWriter os) throws IOException {
        int nbOfPoints = r0.getProfile().getNbrOfXvals();
        os.write("LIMITS OFF"+NL);
        os.write("3DMESH"+NL);
        os.write("2"+NL);
        os.write(nbOfPoints+NL);

        
        writeLocal3d(r0, os);
        writeLocal3d(r1, os);

   //     os.write(NL);
        
        
//        writeLocal3d(profile, scale, x0, teThickness, teStartAt, os);
//        os.write(NL);
//        os.write("LINE"+NL);
//        FloatPair fp = profile.getFirstPair();
//        os.write(f(fp.getMa()*scale)+","+f(fp.getMb()*scale)+NL);
//        fp = profile.getLastPair();
//        os.write(f(fp.getMa()*scale)+","+f(fp.getMb()*scale)+NL);
//        os.write(NL);
    }    

}
