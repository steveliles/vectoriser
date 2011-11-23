package com.sjl.vector.preprocess;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class ColourSmoother
{
    public static void main(String[] anArgs) 
    throws Exception {
        ColourSmoother _vpp = new ColourSmoother();
        
        BufferedImage _img = ImageIO.read(new File(anArgs[0]));
        int _lensWidth = getInt(anArgs, 1, 4);
        int _lensHeight = getInt(anArgs, 2, 4);
        
        ImageIO.write(_vpp.preProcess(_lensWidth, _lensHeight, _img), "png", new File(anArgs[1]));
    }
    
    private static int getInt(String[] aStrings, int anIndex, int aDefaultValue) {
        if (aStrings.length > anIndex) {
            return Integer.parseInt(aStrings[anIndex]);            
        }
        return aDefaultValue;
    }
    
    public BufferedImage preProcess(int aLensWidth, int aLensHeight, final BufferedImage anImage)
    throws Exception {
        ImageProcessor _p = new ImageProcessor();
        
        BufferedImage _result = new BufferedImage(
            anImage.getWidth(), anImage.getHeight(), anImage.getType());
        final WritableRaster _writable = _result.getRaster();
        
        _p.visitPixels(aLensWidth, aLensHeight, anImage, new ImageProcessor.PixelCallback() {
            public void with(int anX, int aY, int[] aPixels) {
                _writable.setPixel(anX, aY, getPixelColour(aPixels));                
            }            
        });
        
        return _result;
    }
    
    private int[] getPixelColour(int[] aSurrounding) {
        int[] _result = new int[3];        
        _result[0] = count(0, aSurrounding);
        _result[1] = count(1, aSurrounding);
        _result[2] = count(2, aSurrounding);        
        return _result;
    }
    
    private int count(int anOffset, int[] aSurrounding) {
        ColourCalculator _c = new ColourCalculator.WeightedAverage();
        int _length = aSurrounding.length-(2-anOffset);
        for (int i=anOffset; i<_length; i+=3) {
            _c.add(aSurrounding[i]);
        }
        return _c.calculate();
    }   
}
