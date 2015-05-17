package com.tada.slantfoil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EdgeShape {

	private ArrayList<Double> y = new ArrayList<Double>();
	private ArrayList<Double> z = new ArrayList<Double>();

	public void populate(BufferedReader textReader) throws IOException {
		String line;

		while ((line = textReader.readLine()) != null) {
			Scanner scanner = new Scanner(line);
			if (scanner.hasNextFloat()) {
				y.add(scanner.nextDouble());
				z.add(scanner.nextDouble());
			}
		}
	}

	public int getLength() {
		if (y.size() != z.size()) {
			throw new RuntimeException("Different size.");
		}
		return y.size();
	}

	public double[] getZ() {
		return toDouble(z);
	}

	public double[] getY() {
		return toDouble(y);
	}

    public static float[] toFloat(ArrayList<Float> floatList) {
        float[] floatArray = new float[floatList.size()];
        for (int i = 0; i < floatList.size(); i++) {
            Float f = floatList.get(i);
            floatArray[i] = (f != null ? f : Float.NaN); // Or whatever default
                                                            // you want.
        }
        return floatArray;
    }

    public static double[] toDouble(ArrayList<Double> floatList) {
        double[] floatArray = new double[floatList.size()];
        for (int i = 0; i < floatList.size(); i++) {
            Double f = floatList.get(i);
            floatArray[i] = (f != null ? f : Double.NaN); // Or whatever default
                                                            // you want.
        }
        return floatArray;
    }

}
