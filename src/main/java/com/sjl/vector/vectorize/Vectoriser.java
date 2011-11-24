package com.sjl.vector.vectorize;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import org.jdom.output.*;

public class Vectoriser {
    
    public static void main(String[] anArgs) 
    throws Exception {
        Vectoriser _v = new Vectoriser();
        SVG _svg = _v.vectorize(ImageIO.read(new File(anArgs[0])));
        
        XMLOutputter _out = new XMLOutputter(Format.getPrettyFormat());
        _out.output(_svg.toPathXML(), new BufferedOutputStream(new FileOutputStream(new File(anArgs[1]))));
    }
    
    public Vectoriser() {}
    
    public SVG vectorize(BufferedImage anImage)
    throws Exception {
        SVG _svg = new SVG(anImage.getWidth(), anImage.getHeight());
        Raster _raster = anImage.getData();
     
        Pixel _start = null;
        Pixel _previous = null;
        Pixel _current = null;
        
        int _limit = anImage.getWidth()-1;
        
        for (int y=0; y<anImage.getHeight(); y++) {
            for (int x=0; x<anImage.getWidth(); x++) {                                
                
                // set the current pixel values
                int[] _colour = _raster.getPixel(x, y, new int[3]);                
                _current = new Pixel(new Point(x, y), Colour.get(_colour));
                
                if (_current.isSameColourAs(_previous)) {
                    if (x == _limit) {
                        _svg.addChord(_start.getPoint(), _current.getPoint(), _start.getColour());
                        _previous = null;
                        _start = null;
                    } else {
                        // skip, we're in the middle of a chord
                        _previous = _current;
                    }                    
                } else {
                    if (_start != null) {
                        _svg.addChord(_start.getPoint(), _previous.getPoint(), _start.getColour());
                    }
                    _start = _current;
                    _previous = _current;
                }
            }
        }
        
        return _svg;
    }
}
