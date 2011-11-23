package com.sjl.vector.vectorize;

import java.util.*;

public class ColourMapImpl implements ColourMap {
    
    private Colour colour;        
    private List<Chord> chords;        
    
    public ColourMapImpl(Colour aColour) {
        colour = aColour;
        chords = new ArrayList<Chord>();
    }

    public void addChord(Chord aChord) {
        chords.add(aChord);                
    }

    public List<Shape> getShapes() {
        Shapes _shapes = new Shapes();
        
        for (Chord _c : chords) {
            Shape _s = _shapes.getTouchingShape(_c);
            if (_s == null) {
                _s = new Shape(colour);
                _shapes.add(_s);
            }
            _s.add(_c);
        }
        
        List<Shape> _unsimplified = _shapes.asList();
        List<Shape> _result = new ArrayList<Shape>();
        for (Shape _s : _unsimplified) {
            _result.addAll(_s.simplify());            
        }
        
        return _result;
    }
    
    class Shapes {
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
                if (_s.touches(aChord)) {
                    // prevent complex shapes by only allowing the left-most chord
                    // to attach even if more chords are also connected in practice
                    // - results in more smaller shapes, but makes it much easier to
                    // create the vector shapes later
                    return (_s.last().getEnd().getY() == aChord.getEnd().getY()) ? null : _s;
                }
            }
            return null;
        }
        
        public List<Shape> asList() {
            return shapes;
        }
    }
}
