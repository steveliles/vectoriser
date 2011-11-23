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
        if (chords.isEmpty())
            return new ArrayList<Shape>();
                
        List<Point> _points = new ArrayList<Point>();        
        for (Chord _c : chords) {
            _points.add(new Point(_c.getEnd().getX()+1, _c.getEnd().getY()-1));
        }        
                
        Point _p = last().getEnd();
        _points.add(new Point(_p.getX(), _p.getY()));
        _p = last().getStart();
        _points.add(new Point(_p.getX()-1, _p.getY()));
        
        for (int i=chords.size()-1; i>=0; i--) {
            _p = chords.get(i).getStart();
            _points.add(new Point(_p.getX()-1, _p.getY()));
        }
        
        Shape _shape = new Shape(colour);
        Point _prev = new Point(first().getStart().getX()-1, first().getStart().getY()-1);
        for (Point _current : _points) {
            _shape.add(new Chord(_prev, _current));
            _prev = _current;
        }
        
        _shape.collapse();
        
        List<Shape> _result = new ArrayList<Shape>();        
        _result.add(_shape);
        return _result;
    }    
         
    private void collapse() {        
        List<Chord> _chords = new ArrayList<Chord>();
        
        Chord _prev = chords.get(0);
                
        boolean _needsWrite = false;
        for (Chord _c : chords) {
            if (_c.sameDirectionAs(_prev, 0)) {
                // skip
                _needsWrite = true;
            } else {
                _chords.add(new Chord(_prev.getStart(), _c.getStart()));
                _prev = _c;        
                _needsWrite = false;
            }
        }
        
        if (_needsWrite)
            _chords.add(new Chord(_prev.getStart(), chords.get(0).getStart()));
        
        chords = _chords;
    }    
}