package com.sjl.vector;

import java.util.*;

import org.junit.*;

import com.sjl.vector.vectorize.*;

public class ColourMapImplTest
{
    @Test
    public void testCreatesShapeWhenOnlyOneExists() {
        ColourMapImpl _cmi = new ColourMapImpl(Colour.get(255,0,0));
        
        _cmi.addChord(new Chord(new Point(0,0), new Point(1,0)));
        _cmi.addChord(new Chord(new Point(0,1), new Point(1,1)));
        
        List<Shape> _shapes = _cmi.getShapes();
        
        Assert.assertNotNull(_shapes);
        Assert.assertEquals(1, _shapes.size());
        
        Shape _shape = _shapes.get(0);
        List<Chord> _chords = _shape.getChords();
        
        Assert.assertTrue(_chords.contains(new Chord(new Point(0,0), new Point(1,0))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(0,1), new Point(1,1))));
    }
    
    @Test
    public void testCreatesMultipleDisjointShapes() {
        ColourMapImpl _cmi = new ColourMapImpl(Colour.get(255,0,0));
        
        // shape 1
        _cmi.addChord(new Chord(new Point(0,0), new Point(1,0)));
        _cmi.addChord(new Chord(new Point(0,1), new Point(1,1)));        
        
        // shape 2
        _cmi.addChord(new Chord(new Point(3,0), new Point(6,0)));               
        _cmi.addChord(new Chord(new Point(3,1), new Point(6,1)));
        
        List<Shape> _shapes = _cmi.getShapes();
        
        Assert.assertNotNull(_shapes);
        Assert.assertEquals(2, _shapes.size());
        
        Shape _shape = _shapes.get(0);
        List<Chord> _chords = _shape.getChords();
        
        Assert.assertTrue(_chords.contains(new Chord(new Point(0,0), new Point(1,0))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(0,1), new Point(1,1))));
        
        _shape = _shapes.get(1);
        _chords = _shape.getChords();
        
        Assert.assertTrue(_chords.contains(new Chord(new Point(3,0), new Point(6,0))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(3,1), new Point(6,1))));
    }
    
    @Test
    public void testCreatesSingleShapeWhenDiagonallyConnected() {
        ColourMapImpl _cmi = new ColourMapImpl(Colour.get(255,0,0));
        
        _cmi.addChord(new Chord(new Point(5,5), new Point(10,10)));        
        _cmi.addChord(new Chord(new Point(11,11), new Point(20,11)));
        
        List<Shape> _shapes = _cmi.getShapes();
        
        Assert.assertNotNull(_shapes);
        Assert.assertEquals(1, _shapes.size());
        
        Shape _shape = _shapes.get(0);
        List<Chord> _chords = _shape.getChords();
        
        Assert.assertTrue(_chords.contains(new Chord(new Point(5,5), new Point(10,10))));
        Assert.assertTrue(_chords.contains(new Chord(new Point(11,11), new Point(20,11))));
    }
}
