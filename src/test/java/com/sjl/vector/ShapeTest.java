package com.sjl.vector;

import java.util.*;

import org.junit.*;

import com.sjl.vector.vectorize.*;

public class ShapeTest
{
    
    // TODO: dsl for building shapes ;)    
    
    @Test
    public void testCollapsesToClockwiseChordsInsteadOfHorizontal() {
        Shape _s = new Shape(Colour.get(0,0,0));
        _s.add(new Chord(new Point(1,1), new Point(2,1)));
        _s.add(new Chord(new Point(1,2), new Point(2,2)));
        
        _s.optimise();
        
        List<Chord> _chords = _s.getChords();

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
        
        _s.optimise();
        
        List<Chord> _chords = _s.getChords();
        
        Assert.assertTrue(_chords.contains(new Chord(new Point(1,1), new Point(8,1))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(8,1), new Point(8,8))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(8,8), new Point(1,8))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(1,8), new Point(1,1))));
    }
    
    
}
