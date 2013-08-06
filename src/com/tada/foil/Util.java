package com.tada.foil;

import java.util.Locale;

public class Util {
    
    public final static String NL = System.getProperty("line.separator");
    
    public static String f(float arg) {
        String sX = String.format(Locale.US, "%2.7f", arg);

        return sX;
    }
    public static String f(double arg) {
        String sX = String.format(Locale.US, "%2.7f", arg);

        return sX;
    }
}
