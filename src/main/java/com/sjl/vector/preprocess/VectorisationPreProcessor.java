package com.sjl.vector.preprocess;

import java.awt.image.*;

public class VectorisationPreProcessor
{
    public BufferedImage preProcess(BufferedImage anImage)
    throws Exception {        
        Raster _raster = anImage.getData();
        
        BufferedImage _result = new BufferedImage(
            anImage.getWidth(), anImage.getHeight(), anImage.getType());
        WritableRaster _writable = _result.getRaster();

        int _blockWidth = 8;
        int _blockHeight = 8;
        
        int _halfWidth = _blockWidth / 2;
        int _halfHeight = _blockWidth / 2; 
        
        int[] _lens = new int[(_blockWidth * _blockHeight) * 3];
                
        for (int y=0; y<anImage.getHeight(); y++) {
            for (int x=0; x<anImage.getWidth(); x++) {               
                _lens = _raster.getPixels(
                    Math.max(0, x-_halfWidth), 
                    Math.max(0, y-_halfHeight), 
                    Math.min(anImage.getWidth()-x, _blockWidth), 
                    Math.min(anImage.getHeight()-y, _blockHeight), 
                    _lens
                );                                                
                _writable.setPixel(x, y, getPixelColour(_lens));                
            }
        }

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
