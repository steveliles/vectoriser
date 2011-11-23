package com.sjl.vector.preprocess;

import java.awt.*;
import java.awt.image.*;

public class PaletteReducer
{
    public BufferedImage reducePalette(BufferedImage anImage) {
        BufferedImage _result = new BufferedImage(
            anImage.getWidth(), anImage.getHeight(),
            BufferedImage.TYPE_BYTE_INDEXED,
            createPalette(anImage)
        );
        
        Graphics2D g2 = _result.createGraphics();
        g2.drawImage(anImage, 0, 0, null);
        g2.dispose();

        return _result; 
    }
    
    private IndexColorModel createPalette(BufferedImage anImage) {
        
        // Use an ImageProcessor to visit the image and calculate a colour palette
        
        
        return new IndexColorModel(
                5, // 32 colours max (5-bit colour depth)
                32, // 32 colour palette
                //          RED  GREEN1 GREEN2  BLUE  WHITE BLACK              
                new byte[]{-100,     0,     0,    0,    -1,     0},
                new byte[]{   0,  -100,    60,    0,    -1,     0},
                new byte[]{   0,     0,     0, -100,    -1,     0}
        );
    }
}
