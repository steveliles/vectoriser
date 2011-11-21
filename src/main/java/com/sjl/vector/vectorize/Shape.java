package com.sjl.vector.vectorize;

import java.util.*;

public class Shape implements Iterable<Chord> {

    private Colour colour;
    private List<Chord> chords;
    
    public Shape(Colour aColour) {
        this(aColour, new ArrayList<Chord>());
    }
    
    public Shape(Colour aColour, List<Chord> aChords) {
        colour = aColour;
        chords = aChords;
    }
    
    public void add(Chord aChord) {
        chords.add(aChord);
    }
    
    public boolean touches(Chord aChord) {
        for (int i=chords.size()-1; i>=0; i--) {
            if (chords.get(i).touches(aChord))
                return true;
        }
        return false;
    }    
    
    public Colour getColour() {
        return colour;
    }
    
    public Iterator<Chord> iterator() {
        return chords.iterator();
    }
    
    public List<Chord> getChords() {
        return chords;
    }
    
    @Override
    public String toString() {
        return "!" + colour + "!" + chords + "!";
    }
    
    public List<Shape> simplify() {
        Shapes _shapes = new Shapes();
                        
        Chord _previous = null;        
        for (Chord _current : chords) {
            if (isNewScanLine(_current, _previous)) {
                Shape _shape = _shapes.getTouchingShape(_current);
                if (_shape == null) {
                    _shape = new Shape(colour);
                    _shapes.add(_shape);
                }
                _shape.add(_current);                               
            } else {
                Shape _shape = new Shape(colour);
                _shape.add(_current);
                _shapes.add(_shape);
            }
            _previous = _current;
        }
        
        List<Shape> _result = _shapes.asList();
        for (Shape _s : _result)
            _s.optimise();
        
        return _result;
    }        
    
    private void optimise() {     
        reOrder();
        collapse();
    }
    
    // re-organize the chords so that we follow a path around the outline
    // of the object instead of through scanlines
    private void reOrder()
    {
        List<Chord> _chords = new ArrayList<Chord>();
        
        Chord _previous = new Chord(
            chords.get(0).getStart(), 
            chords.get(0).getStart() // a trick
        );
                
        for (Chord _c : chords) {
            _chords.add(new Chord(_previous.getEnd(), _c.getEnd()));
            _previous = _c;
        }
        
        for (int i=chords.size()-1; i>=0; i--) {
            Chord _c = new Chord(_previous.getEnd(), chords.get(i).getStart());
            _chords.add(_previous = _c);      
        }
        
        chords = _chords;
    }
    
    private void collapse() {
        List<Chord> _chords = new ArrayList<Chord>();
        
        Chord _prev = new Chord(
            chords.get(0).getStart(), 
            chords.get(0).getEnd()
        );
                
        for (Chord _c : chords) {
            if (_c.sameDirectionAs(_prev)) {
                // skip
            } else {
                _chords.add(new Chord(_prev.getStart(), _c.getStart()));
                _prev = _c;                                
            }
        }
        
        _chords.add(new Chord(_prev.getStart(), chords.get(0).getStart()));
        
        chords = _chords;
    }
    
    private boolean isNewScanLine(Chord aCurrent, Chord aPrevious) {
        if (aPrevious == null)
            return true;
        
        return (aCurrent.getStart().getY() != aPrevious.getStart().getY());
    }
    
    private class Shapes {
        private List<Shape> shapes;
        
        public Shapes() {
            shapes = new ArrayList<Shape>();
        }
        
        public void add(Shape aShape) {
            shapes.add(aShape);
        }
        
        public Shape getTouchingShape(Chord aChord) {
            for (int i=shapes.size()-1; i>=0; i--) {
                Shape _s = shapes.get(i);
                if (_s.touches(aChord))
                    return _s;
            }
            return null;
        }
        
        public List<Shape> asList() {
            return shapes;
        }
    }
}
