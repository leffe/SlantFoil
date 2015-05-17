package com.tada.slantfoil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.tada.foil.BaseProfile;
import com.tada.foil.Float3dPoint;
import com.tada.foil.DoublePair;
import com.tada.foil.WingSurface;
import com.tada.foil.WriteProfile;

public class Ribs {

    private String mLabel;
    private WingSurface mSurface;
    private List<RibSet> mList = new ArrayList<RibSet>();
    
    public Ribs(WingSurface upperSurface, String label) {
        mLabel = label;
        mSurface = upperSurface;
    }

    public void add(double position, double angle) {
        mList.add(new RibSet(mSurface, position, angle));
    }
    
    public void add(Parameters parameters) {

        for (int idx = 0; idx < parameters.getNbrOfParameters(); idx ++) {
            add(parameters.getParamPosition(idx), parameters.getParamAngle(idx));
        }
    }
    
    private int getNbrOfRibs() {
        return mList.size();
    }
    
    private Float3dPoint[] getPoints(RibSet rs) {
        return null;
    }
    
    public void write(String path) throws IOException {
        OutputStreamWriter os;
        for (RibSet set : mList) {
            BaseProfile profile = set.getProfile();
            String fullPath = path + mLabel + "_at_" + fmt(set.mPosition) + "_" + fmt(set.mAngle) + "_degree";
            os = getOutputStream(new File(fullPath + ".txt"));
            WriteProfile.write(profile, 1f, os);
            os.close();
            os = getOutputStream(new File(fullPath + "_scale.txt"));
            WriteProfile.write(profile, mSurface.getCordAt(set.mPosition)    , os);
            os.close();
            os = getOutputStream(new File(fullPath + ".scr"));
            WriteProfile.writeAutocad(profile, mSurface.getmTeThickness(), mSurface.getmTeStartAt(), os);
            os.close();
        }
        String fullPath = path + mLabel + "_Mesh_";
        os = getOutputStream(new File(fullPath + ".scr"));
        for (RibSet rib : mList) {
            if (mList.indexOf(rib)+1 == mList.size()) {break;}
            RibSet nextRib = mList.get(mList.indexOf(rib)+1); 
            WriteProfile.writeAutocadMesh(rib, nextRib, mSurface, os);
        }
        os.close();
    }
    
    
    private static OutputStreamWriter getOutputStream(File file) throws IOException {
        FileOutputStream fos;
        OutputStreamWriter out = null;
        try {
            fos = new FileOutputStream(file);
            out = new OutputStreamWriter(fos, "UTF-8");
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e);
        }
        return out;
    }
    
    public static String fmt(float arg) {
        String sX = String.format("%2.3f", arg);

        return sX;
    }

    public static String fmt(double arg) {
        return fmt((float)arg);
    }
    public static double rtp = (Math.PI/180);

    public static String fmtdeg(double arg) {
        return fmt((float)(arg/rtp));
    }



}
