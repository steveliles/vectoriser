package com.sjl.vector.vectorize;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import com.sjl.vector.preprocess.*;

/**
 * Produce a raster image that _looks_ like the effect we get from
 * vectorising an image. This provides ideal input to feed into the 
 * vectoriser, but also gives a nice comic-book effect to photos.
 * 
 * @author steve
 */
public class VectoriseEffect {
    
    public static void main(String[] anArgs) 
    throws Exception {        
        PaletteReducer _pr = new PaletteReducer();
        ColourSmoother _cs = new ColourSmoother();
        
        int _lensWidth = getInt(anArgs, 1, 4);
        int _lensHeight = getInt(anArgs, 2, 4);
        
        BufferedImage _img = ImageIO.read(new File(anArgs[0]));
        _img = _pr.reducePalette(_img);
        _img = _cs.preProcess(_lensWidth, _lensHeight, _img);
        
        ImageIO.write(_img, "png", new File(anArgs[3]));
    }
    
    private static int getInt(String[] aStrings, int anIndex, int aDefaultValue) {
        if (aStrings.length > anIndex) {
            return Integer.parseInt(aStrings[anIndex]);            
        }
        return aDefaultValue;
    }
    
}
