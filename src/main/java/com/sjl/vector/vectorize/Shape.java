package com.sjl.vector.vectorize;

import java.util.*;

public class Shape implements Iterable<Chord>, Comparable<Shape> {

    private Colour colour;
    private List<Chord> chords;
    private int zIndex = 1;
    
    public Shape(Colour aColour) {
        this(aColour, new ArrayList<Chord>());       
    }
    
    public Shape(Colour aColour, List<Chord> aChords) {
        colour = aColour;
        chords = aChords;
    }
    
    public void setZIndex(int aZIndex) {
        zIndex = aZIndex;
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
    
    public Chord first() {
        return chords.get(0);
    }
    
    public Chord last() {
        return chords.get(chords.size()-1);
    }
    
    @Override
    public int compareTo(Shape aShape) {
        int _z = zIndex - aShape.zIndex;
        if (_z == 0) {
            Point _p1 = first().getStart();
            Point _p2 = aShape.first().getStart();
            
            return (_p1.getX() > _p2.getX()) ? 1 : -1;
        } else {
            return _z;
        }
    }

    public List<Shape> simplify() {   
        removeCompletelyNestedShapes();
        List<Shape> _simpleShapes = segmentComplexShapes();
        
        List<Shape> _result = new ArrayList<Shape>();
        for (Shape _s : _simpleShapes) {
            _result.addAll(_s.outline());
        }
        
        return _result;
    }
    
    private List<Shape> outline() {        
        if (chords.isEmpty())
            return new ArrayList<Shape>();
                
        List<Point> _points = new ArrayList<Point>();        
        for (Chord _c : chords) {
            _points.add(_c.getEnd());
        }
        
        for (int i=chords.size()-1; i>=0; i--) {
            _points.add(chords.get(i).getStart());
        }
        
        Shape _shape = new Shape(colour);
        Point _prev = first().getStart();
        for (Point _current : _points) {
            _shape.add(new Chord(_prev, _current));
            _prev = _current;
        }
        
        _shape.collapse();
        
        List<Shape> _result = new ArrayList<Shape>();        
        _result.add(_shape);
        return _result;
    }
        
    private List<Shape> segmentComplexShapes() {
        Shapes _shapes = new Shapes();
        
        Shape _main = new Shape(colour);
        Chord _previous = null;
        for (Chord _current : chords) {
            if (isNewScanLine(_current, _previous)) {
                _main.add(_current);                
            } else {
                Shape _s = _shapes.getTouchingShape(_current);
                if (_s == null) {
                    _s = new Shape(colour);
                    _shapes.add(_s);
                }
                _s.add(_current);
            }
            _previous = _current;
        }
        
        List<Shape> _result = _shapes.asList();
        _result.add(_main);
       
        return _result;
    }

    private void removeCompletelyNestedShapes() {
        Shapes _nested = new Shapes();
                        
        Chord _previous = null;        
        for (Chord _current : chords) {
            if (isNewScanLine(_current, _previous)) {
                // nuthin'                
            } else {
                Chord _inner = new Chord(_previous.getEnd(), _current.getStart());
                Shape _s = _nested.getTouchingShape(_inner);
                if (_s == null) {
                    _s = new Shape(colour);
                    _nested.add(_s);
                }
                _s.add(_inner);
            }
            _previous = _current;
        }
        
        for (Shape _s : _nested.asList()) {
            if (completelyContains(_s)) {
                remove(_s);
                zIndex--;
            }
        }
    }
    
    private boolean completelyContains(Shape aShape) {
        Chord _bottom = aShape.last();        
        for (Chord _c : chords) {
            if (_c.getStart().getY() == _bottom.getStart().getY()+1) {
                if (
                    (_c.getStart().getX() <= _bottom.getStart().getX()) &&
                    (_c.getEnd().getX() >= _bottom.getEnd().getX())
                ) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void remove(Shape aShape) {        
        for (Chord _c : aShape.getChords()) {
            Chord _before = findChordEndingAt(_c.getStart());
            Chord _after = findChordStartingAt(_c.getEnd());
            
            _before.setEnd(_after.getEnd());
            chords.remove(_after);
        }
    }
    
    private Chord findChordEndingAt(Point aPoint) {
        for (Chord _c: chords) {
            if (_c.getEnd().equals(aPoint))
                return _c;
        }
        return null;
    }

    private Chord findChordStartingAt(Point aPoint) {
        for (Chord _c: chords) {
            if (_c.getStart().equals(aPoint))
                return _c;
        }
        return null;
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