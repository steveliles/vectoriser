package com.sjl.vector.preprocess;

import java.awt.image.*;

public class ImageProcessor {
    
    interface PixelCallback {
        void with(int anX, int aY, int[] aPixels);
    }
    
    public void visitPixels(
        int aLensWidth, int aLensHeight, 
        BufferedImage anImage, PixelCallback aCallback
    ) {
        int _halfWidth = aLensWidth / 2;
        int _halfHeight = aLensHeight / 2; 
        
        Raster _raster = anImage.getData();
        int[] _lens = new int[(aLensWidth * aLensHeight) * 3];
                
        for (int y=0; y<anImage.getHeight(); y++) {
            for (int x=0; x<anImage.getWidth(); x++) {               
                _lens = _raster.getPixels(
                    Math.max(0, x-_halfWidth), 
                    Math.max(0, y-_halfHeight), 
                    Math.min(anImage.getWidth()-x, aLensWidth), 
                    Math.min(anImage.getHeight()-y, aLensHeight), 
                    _lens
                );                                                
                aCallback.with(x, y, _lens);
            }
        }
    }
}
