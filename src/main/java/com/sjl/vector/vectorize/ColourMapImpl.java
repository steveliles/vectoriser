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
        Shapes _result = new Shapes();
        
        for (Chord _c : chords) {
            Shape _s = _result.getTouchingShape(_c);
            if (_s == null) {
                _s = new Shape(colour);
                _result.add(_s);
            }
            _s.add(_c);
        }
        
        return _result.asList();
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
