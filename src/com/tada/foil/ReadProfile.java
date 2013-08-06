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

public class ReadProfile {

    List<DoublePair> mUpper;
    List<DoublePair> mLower;

    public enum Surface {UPPER, LOWER};
    
    public ReadProfile(File inputFile) {
        readProfileUpperAndLower(inputFile);
    }

    public ReadProfile(Surface surface, File inputFile) {
        readProfile(surface, inputFile);
    }

    public List<DoublePair> getUpper() {
        return mUpper;
    }

    public List<DoublePair> getLower() {
        return mLower;
    }

    private void readProfileUpperAndLower(File inputFile) {
        ArrayList<DoublePair> list = new ArrayList<DoublePair>();
        try {
            FileReader fr = new FileReader(inputFile);
            BufferedReader textReader = new BufferedReader(fr);
            try {
                String line;
                int center = 0;
                while ((line = textReader.readLine()) != null) {
                    if (line.contains("#")) continue;
                    Scanner scanner = new Scanner(line);
                    if (scanner.hasNextFloat()) {
                        float x = scanner.nextFloat();
                        float y = scanner.nextFloat();
                        list.add(new DoublePair(x, y));
                        if (x == 0f) {
                            center = list.size();
                        }
                    }
                }
                mUpper = list.subList(0, center);
                mLower = list.subList(center - 1, list.size());
                // Reverse order of top
                mUpper = reverse(mUpper);
                textReader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void readProfile(Surface surface, File inputFile) {
        ArrayList<DoublePair> list = new ArrayList<DoublePair>();
        try {
            FileReader fr = new FileReader(inputFile);
            BufferedReader textReader = new BufferedReader(fr);
            try {
                String line;
                while ((line = textReader.readLine()) != null) {
                    Scanner scanner = new Scanner(line);
                    if (scanner.hasNextFloat()) {
                        float x = scanner.nextFloat();
                        float y = scanner.nextFloat();
                        list.add(new DoublePair(x, y));
                    }
                }
                textReader.close();
                switch (surface) {
                case UPPER : mUpper = list;
                break;
                case LOWER : mLower = list;
                break;
                }
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
}
