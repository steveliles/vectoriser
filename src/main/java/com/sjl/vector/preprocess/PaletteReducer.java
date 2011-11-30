package com.sjl.vector.preprocess;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

/**
 * Taking a deliberately very simple approach to reducing the colour palette.
 * To achieve the "vectorised" effect (and indeed as a pre-processing step for
 * vectorisation) we want to deliberately pool areas of colour, and limit small 
 * areas of subtle colour shifts, so we want a standard palette with reasonable
 * distance between adjacent colours.
 */
public class PaletteReducer
{
    public static void main(String[] anArgs) 
    throws Exception {
        PaletteReducer _pr = new PaletteReducer();
        
        BufferedImage _img = ImageIO.read(new File(anArgs[0]));
        
        ImageIO.write(_pr.reducePalette(_img), "png", new File(anArgs[1]));
    }
    
    public BufferedImage reducePalette(BufferedImage anImage) {
        BufferedImage _reduced = new BufferedImage(
            anImage.getWidth(), anImage.getHeight(),
            BufferedImage.TYPE_BYTE_INDEXED,
            createPalette(anImage)
        );
        
        Graphics2D _ctx = _reduced.createGraphics();
        _ctx.drawImage(anImage, 0, 0, null);
        _ctx.dispose();
        
        BufferedImage _result = new BufferedImage(
            anImage.getWidth(), anImage.getHeight(),
            BufferedImage.TYPE_INT_RGB
        );
        
        _ctx = _result.createGraphics();
        _ctx.drawImage(_reduced, 0, 0, null);
        _ctx.dispose();

        return _result; 
    }
       
    private IndexColorModel createPalette(BufferedImage anImage) {
        byte[][] _colours = createArray();
        
        return new IndexColorModel(
                8, // 8-bit colour depth
                256, // 256 colour palette
                _colours[0], // R 
                _colours[1], // G
                _colours[2]  // B
        );
    }
    
    /**
     * Construct a simple colour palette of 256 colours with
     * 8 levels of red and green, and 4 levels of blue (because
     * the human eye is less sensitive to blue).
     */
    private byte[][] createArray() {
        byte[][] _result = new byte[3][256];
        int _index = 0;
        for (byte r=0; r<8; r++) {
            for (byte g=0; g<8; g++) {
                for (byte b=0; b<4; b++) {
                    _result[0][_index] = (byte)(r*32);
                    _result[1][_index] = (byte)(g*32);
                    _result[2][_index] = (byte)(b*64); 
                    _index++;
                }
            }
        }
        return _result;
    }
}
