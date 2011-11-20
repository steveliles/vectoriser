package com.sjl.vector.vectorize;

import java.awt.image.*;
import java.util.*;

public class Colour {
    
    private static List<Colour> colours = new ArrayList<Colour>();
    
    public static Colour get(int aRed, int aGreen, int aBlue) {
        Colour _c1 = new Colour(aRed, aGreen, aBlue);
        for (Colour _c2 : colours) {
            if (_c2.equals(_c1)) {
                return _c2;
            }
        }
        colours.add(_c1);
        return _c1;
    }
    
    public static Colour get(int[] aColour) {
        return get(aColour[0], aColour[1], aColour[2]);
    }
    
    private int red, green, blue;
    
    private Colour() {
        this(-1,-1,-1);
    }
    
    private Colour(int aRed, int aGreen, int aBlue) {
        red = aRed;
        green = aGreen;
        blue = aBlue;
    }
    
    public void setFromRaster(Raster aRaster, int anX, int aY) {
        int[] _colour = aRaster.getPixel(anX, aY, new int[3]);
        red = _colour[0];
        green = _colour[1];
        blue = _colour[2];
    }
    
    public int getRed() {
        return red;
    }
    
    public int getGreen() {
        return green;
    }
    
    public int getBlue() {
        return blue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + blue;
        result = prime * result + green;
        result = prime * result + red;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Colour other = (Colour) obj;
        if (blue != other.blue)
            return false;
        if (green != other.green)
            return false;
        if (red != other.red)
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "#" + pad(Integer.toHexString(red)) + pad(Integer.toHexString(green)) + pad(Integer.toHexString(blue));
    }
    
    private String pad(String aHexString) {
        return (aHexString.length() == 2) ? aHexString : "0" + aHexString;
    }
}