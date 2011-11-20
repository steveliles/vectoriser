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
    
    public void optimise() {
//System.out.println("initial: " + chords);        
        reOrder();
//System.out.println("re-ordered: " + chords);        
        collapse();
//System.out.println("collapsed: " + chords);      
    }

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
}
