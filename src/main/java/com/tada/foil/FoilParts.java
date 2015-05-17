package com.tada.foil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import com.sderhy.RawSplineDouble;
import com.tada.slantfoil.EdgeShape;

/**
 * [OLD!!!]
 * Holds a foil in an form where the z coordinate can be returned for a given
 * x coordinate. For one x there are two z values, one for the upper shape 
 * and one for the lower shape.
 * x coordinates are in the range [0..1]. 
 */
public class FoilParts {

	private RawSplineDouble mTopSpline;
	private RawSplineDouble mBottomSpline;

	public enum FoilPart {UPPER,LOWER};
	
	public float getZ(float x, FoilPart part) {
		return 0.0f;
	}
	
	public FoilParts (File inputFile) {
		List<DoublePair> top;
		List<DoublePair> bottom;
		ArrayList<DoublePair> list = new ArrayList<DoublePair>();

		try {
				FileReader fr = new FileReader(inputFile);
				BufferedReader textReader = new BufferedReader(fr);
	
				try {
					String line;
					int center = 0;
					while ((line = textReader.readLine()) != null) {
						Scanner scanner = new Scanner(line);
						if (scanner.hasNextFloat()) {
							float y = scanner.nextFloat();
							float z = scanner.nextFloat();
							list.add(new DoublePair(y, z));
							if (y == 0f) {
								center = list.size();
							}
						}
					}
					
					top = list.subList(0, center);
					bottom = list.subList(center-1, list.size());
					System.out.println("size top:" + top.size());
					System.out.println("size bottom:" + bottom.size());

					
					Iterator<DoublePair> it = top.iterator();
					while (it.hasNext()) {
						System.out.println("top: " + it.next().getMa());
					}
					it = bottom.iterator();
					while (it.hasNext()) {
						System.out.println("bottom: " + it.next().getMa());
					}

					
					
					
					
					// Reverse order of top
					top = reverse(top);
					
					it = top.iterator();
					while (it.hasNext()) {
						System.out.println("rev top: " + it.next().getMa());
					}
					
					
					
					
					
					
					
					textReader.close();
					
	//				spline = new RawSpline(es.getZ(), es.getY(), es.getLength());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	private List<DoublePair> reverse(List<DoublePair> list) {
		List<DoublePair> nl = new ArrayList<DoublePair>();
		ListIterator<DoublePair> it = list.listIterator(list.size());
		while (it.hasPrevious()) {
			DoublePair fp = it.previous();
			nl.add(fp);
		}
		return nl;
	}

//	class FloatPair {
//		float my;
//		float mz;
//		public FloatPair(float y, float z) {
//			my = y;
//			mz = z;
//		}
//		public float getMy() {
//			return my;
//		}
//		public float getMz() {
//			return mz;
//		}
//	}

}
