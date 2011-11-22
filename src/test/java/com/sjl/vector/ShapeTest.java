package com.sjl.vector;

import java.util.*;

import org.junit.*;

import com.sjl.vector.vectorize.*;

public class ShapeTest
{
    @Test
    public void testCollapsesToClockwiseChordsInsteadOfHorizontal() {
        Shape _s = new Shape(Colour.get(0,0,0));
        _s.add(new Chord(new Point(1,1), new Point(2,1)));
        _s.add(new Chord(new Point(1,2), new Point(2,2)));
        
        List<Shape> _shapes = _s.simplify();
        
        Assert.assertEquals(1, _shapes.size());
        
        List<Chord> _chords = _shapes.get(0).getChords();

        Assert.assertTrue(_chords.contains(new Chord(new Point(1,1), new Point(2,1))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(2,1), new Point(2,2))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(2,2), new Point(1,2))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(1,2), new Point(1,1))));
    }
    
    @Test
    public void testCollapsesPointsInStraightLinesToJustEndPoints() {
        Shape _s = new Shape(Colour.get(0,0,0));
        
        _s.add(new Chord(new Point(1,1), new Point(8,1)));
        _s.add(new Chord(new Point(1,2), new Point(8,2)));
        _s.add(new Chord(new Point(1,3), new Point(8,3)));
        _s.add(new Chord(new Point(1,4), new Point(8,4)));
        _s.add(new Chord(new Point(1,5), new Point(8,5)));
        _s.add(new Chord(new Point(1,6), new Point(8,6)));
        _s.add(new Chord(new Point(1,7), new Point(8,7)));
        _s.add(new Chord(new Point(1,8), new Point(8,8)));
        
        List<Shape> _shapes = _s.simplify();
        
        Assert.assertEquals(1, _shapes.size());
        
        List<Chord> _chords = _shapes.get(0).getChords();
        
        Assert.assertTrue(_chords.contains(new Chord(new Point(1,1), new Point(8,1))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(8,1), new Point(8,8))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(8,8), new Point(1,8))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(1,8), new Point(1,1))));
    }
    
    @Test
    public void testDropsInnerShapePaths() {
        Shape _s = new Shape(Colour.get(0,0,0));
        
        _s.add(new Chord(new Point(0,0), new Point(5,0))); // ******
        _s.add(new Chord(new Point(0,1), new Point(5,1))); // ******
        _s.add(new Chord(new Point(0,2), new Point(1,2))); // **--**
        _s.add(new Chord(new Point(4,2), new Point(5,2))); // 
        _s.add(new Chord(new Point(0,3), new Point(1,3))); // **--**
        _s.add(new Chord(new Point(4,3), new Point(5,3))); //
        _s.add(new Chord(new Point(0,4), new Point(5,4))); // ******
        _s.add(new Chord(new Point(0,5), new Point(5,5))); // ******
        
        List<Shape> _shapes = _s.simplify();
        
        Assert.assertEquals(1, _shapes.size());
        
        List<Chord> _chords = _shapes.get(0).getChords();
                
        Assert.assertTrue(_chords.contains(new Chord(new Point(0,0), new Point(5,0))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(5,0), new Point(5,5))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(5,5), new Point(0,5))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(0,5), new Point(0,0))));
    }
    
    @Test
    public void testSimplifiesComplexShapes() {
        Shape _s = new Shape(Colour.get(0,0,0));
        
        _s.add(new Chord(new Point(0,0), new Point(5,0))); // ******
        _s.add(new Chord(new Point(0,1), new Point(5,1))); // ******
        _s.add(new Chord(new Point(0,2), new Point(1,2))); // **--**
        _s.add(new Chord(new Point(4,2), new Point(5,2))); // 
        _s.add(new Chord(new Point(0,3), new Point(1,3))); // **--**
        _s.add(new Chord(new Point(4,3), new Point(5,3))); //        
        
        List<Shape> _shapes = _s.simplify();
        
        Assert.assertEquals(2, _shapes.size());
        
        // shape 1 ----
        List<Chord> _chords = _shapes.get(0).getChords();
        
        Assert.assertTrue(_chords.contains(new Chord(new Point(4,2), new Point(5,2))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(5,2), new Point(5,3))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(5,3), new Point(4,3))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(4,3), new Point(4,2))));
        
        // shape 2 ----
        _chords = _shapes.get(1).getChords();

        Assert.assertTrue(_chords.contains(new Chord(new Point(0,0), new Point(5,0))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(5,0), new Point(5,1))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(5,1), new Point(1,2))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(1,2), new Point(1,3))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(1,3), new Point(0,3))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(0,3), new Point(0,0))));        
    }    
}
