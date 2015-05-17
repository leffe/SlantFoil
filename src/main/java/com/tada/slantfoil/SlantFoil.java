package com.tada.slantfoil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.sderhy.RawSplineDouble;
import com.tada.foil.EdgeProfile;
import com.tada.foil.FoilParts;
import com.tada.foil.WingSurface;
import com.tada.foil.ReadProfile;
import com.tada.foil.WriteProfile;


public class SlantFoil {

    static final String PARAM_FILE = "params.json";
    
	static RawSplineDouble mRootSpline;
	static RawSplineDouble mTipSpline;
	
	static FoilParts mRoot;
	static FoilParts mTip;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    
        Parameters[] pList = getParameters();
        if (pList.length <= 0) {
            System.out.println("Missing parameter file: " + PARAM_FILE);
            return;
        } 
        
        Parameters parameters = null;
	    
        for (int i = 0; i < pList.length; i++) {
            System.out.println("Index:" + i + ", "+ pList[i].getEntryName());
        }

        if (args.length > 0) {
            int idx = Integer.valueOf(args[0]);
            if (pList.length > idx) {
                parameters = pList[idx]; 
            }
        } else {
            parameters = pList[0];
        }
        
        System.out.println("Using: " + parameters.getEntryName());

	    
	    
	    
	    
        String readPath;
        String writePath;
        File rootFile;
        File tipFile;
        ReadProfile profile;
        EdgeProfile rootEdgeUpper;
        EdgeProfile rootEdgeLower;
        EdgeProfile tipEdgeUpper;
        EdgeProfile tipEdgeLower;
        WingSurface upperSurface;
        WingSurface lowerSurface;
        float tipPosition;
        Ribs ribsUpper;
        Ribs ribsLower;

//        /**
//         *  Lansen Stab
//         */
//        readPath = "R:\\Leifs\\Flyg\\Lansen\\Foils";
//        writePath = "R:\\Leifs\\Flyg\\Lansen\\Foils\\out\\";
//        rootFile = new File(readPath+"\\naca64a009s25.txt");
//        tipFile = new File(readPath+"\\naca64a009s25.txt");
//	
//        profile = new ReadProfile(rootFile);
//        rootEdgeUpper = new EdgeProfile(profile.getUpper());
//        
//        profile = new ReadProfile(tipFile);
//        tipEdgeUpper = new EdgeProfile(profile.getUpper());
//        
//        upperSurface = new WingSurface(rootEdgeUpper, tipEdgeUpper);
//        
//        scale = 525.6f;
//        upperSurface.setRootScale(scale);
//        upperSurface.setTipScale(159.6f);
//        upperSurface.setSweep(-301.0f);
//        
//        tipPosition = 502.45f;
//        upperSurface.setSpan(tipPosition);
//        
//        upperSurface.setTrailingEdge(0.5f, 0.45f);
//        
//        ribsUpper = new Ribs(upperSurface, "Stab_rib");
//        
//        
//        ribsUpper.add(96.61f, 101.81f);
//        ribsUpper.add(180.9f, 112.91f);
//        ribsUpper.add(201.24f, 112.91f);
//        ribsUpper.add(224.63f, 112.91f);
//        ribsUpper.add(293.54f, 112.91f);
//        ribsUpper.add(341.76f, 112.91f);
//        ribsUpper.add(394.38f, 112.91f);
//        ribsUpper.add(444.09f, 112.91f);
//        ribsUpper.add(458.45f, 90f);
//        
//        ribsUpper.write(writePath);
//        
      /**
      *  Fin
      */
      readPath = "X:\\Lansen\\Foils";
      writePath = readPath + "\\out\\fin\\";
      rootFile = new File(readPath+"\\naca64a009s25.txt");
      tipFile = new File(readPath+"\\naca64a009s25.txt");
     profile = new ReadProfile(rootFile);
     rootEdgeUpper = new EdgeProfile(profile.getUpper());
     
     profile = new ReadProfile(tipFile);
     tipEdgeUpper = new EdgeProfile(profile.getUpper());
     
     upperSurface = new WingSurface(rootEdgeUpper, tipEdgeUpper);
     upperSurface.setRootScale(parameters.getRootScale());
     upperSurface.setTipScale(parameters.getTipScale());
     upperSurface.setSweep(parameters.getSweep());
     
//     tipPosition = 418.12f;
     upperSurface.setSpan(parameters.getTipPosition());
     
     upperSurface.setTrailingEdge(0.5f, 0.45f);
     
     ribsUpper = new Ribs(upperSurface, parameters.getEntryName());
     
     
     ribsUpper.add(parameters);
     
//     ribsUpper.add(0f, 90f);
//     ribsUpper.add(2.75f, 90f);
//     ribsUpper.add(87.22f, 143.75f);
//     ribsUpper.add(144.14f, 111.075f);
//     ribsUpper.add(tipPosition, 90f);
     
     ribsUpper.write(writePath);
     
//      /**
//      *  Lansen wing
//      */
//     readPath = "R:\\Leifs\\Flyg\\Lansen\\Foils";
//     writePath = "R:\\Leifs\\Flyg\\Lansen\\Foils\\out\\wing\\";
//     rootFile = new File(readPath+"\\naca64a009s25.txt");
//     tipFile = new File(readPath+"\\naca64a009s25.txt");
//
//     profile = new ReadProfile(rootFile);
//     rootEdgeUpper = new EdgeProfile(profile.getUpper());
//     
//     profile = new ReadProfile(tipFile);
//     tipEdgeUpper = new EdgeProfile(profile.getUpper());
//     
//     upperSurface = new WingSurface(rootEdgeUpper, tipEdgeUpper);
//     scale = 868.321f;
//     upperSurface.setRootScale(scale);
//     upperSurface.setTipScale(293.009f);
//     upperSurface.setSweep(-747.75f);
//     
//     tipPosition = 1300f;
//     upperSurface.setSpan(tipPosition);
//     
//     upperSurface.setTrailingEdge(0.5f, 0.45f);
//     
//     ribsUpper = new Ribs(upperSurface, "Lansen_wing");
//     
//     ribsUpper.add(0f, 90f);
//     ribsUpper.add(1025f, 115f);
//     ribsUpper.add(tipPosition, 90f);
//     
//     ribsUpper.write(writePath);
     
//        
//        /**
//         * MyCat Stab mk2
//         */
//        readPath = "R:\\Leifs\\Flyg\\MyCat\\Foils";
//        writePath = "R:\\Leifs\\Flyg\\MyCat\\Foils\\out\\";
//        rootFile = new File(readPath+"\\sd7080_Mod.txt");
//        tipFile = new File(readPath+"\\sd7080_Mod.txt");
//        profile = new ReadProfile(rootFile);
//        rootEdgeUpper = new EdgeProfile(profile.getUpper());
//        rootEdgeLower = new EdgeProfile(profile.getLower());
//        
//        profile = new ReadProfile(tipFile);
//        tipEdgeUpper = new EdgeProfile(profile.getUpper());
//        tipEdgeLower = new EdgeProfile(profile.getLower());
//        
//        upperSurface = new WingSurface(rootEdgeUpper, tipEdgeUpper);
//        lowerSurface = new WingSurface(rootEdgeLower, tipEdgeLower);
//        scale = 270f;
//        upperSurface.setRootScale(scale);
//        upperSurface.setTipScale(scale);
//        upperSurface.setSweep(0f);
//        
//        tipPosition = 530f;
//        upperSurface.setSpan(tipPosition);
//        
//        lowerSurface.copyParams(upperSurface);
//
//        upperSurface.setTrailingEdge(0.5f, 0.45f);
//        lowerSurface.setTrailingEdge(-0.5f, 0.45f);
//        
//        ribsUpper = new Ribs(upperSurface, "StabRibUpper");
//        ribsLower = new Ribs(lowerSurface, "StabRibLower");
//        
//        ribsUpper.add(0f, 90f);
//        ribsLower.add(0f, 90f);
//        ribsUpper.add(tipPosition, 90f);
//        ribsLower.add(tipPosition, 90f);
//        
//        ribsUpper.write(writePath);
//        ribsLower.write(writePath);
        
        
	}


    private static Parameters[] getParameters()
            throws FileNotFoundException, IOException {
        final Gson gson = new Gson();
        String json;
        
        File inFile = new File(PARAM_FILE); 
        FileReader fr = new FileReader(inFile);
        BufferedReader textReader = new BufferedReader(fr);
        StringBuilder line = new StringBuilder();
        while (textReader.ready()) {
            line.append(textReader.readLine());
        }
        textReader.close();
        json = line.toString();
        return gson.fromJson(json, Parameters[].class);
    }

	
    public static RawSplineDouble initSpline(File inputFile) {
        RawSplineDouble spline = null;
	try {
		FileReader fr = new FileReader(inputFile);
		BufferedReader textReader = new BufferedReader(fr);
		EdgeShape es = new EdgeShape();

		try {
			es.populate(textReader);
			textReader.close();
			
			spline = new RawSplineDouble(es.getZ(), es.getY(), es.getLength());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return spline;
}

	
	static void dumpSpline(RawSplineDouble spline, double increments, double scale) {
		for (int idx = 0; idx < spline.GetNumPoints();idx++) {
			
		}
		
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

}
