package com.sjl.vector.vectorize;

public class Chord {
    
    private Point start, end;    

    public Chord(Point aStart, Point anEnd) {
        start = aStart;
        end = anEnd;
    }
    
    public Point getStart() {
        return start;
    }
    
    public Point getEnd() {
        return end;
    }
    
    public boolean touches(Chord aChord) {
        if (start.getY() - aChord.start.getY() > 1)
            return false;
        
        return (
            between(start.getX(), aChord.start.getX(), aChord.end.getX()) ||
            between(end.getX(), aChord.start.getX(), aChord.end.getX())
        );
    }
    
    public boolean sameDirectionAs(Chord aChord) {
        // todo allow some slop?
        return direction().equals(aChord.direction());
    }
    
    public UnitVector direction() {
        int _x = Math.max(start.getX(), end.getX()) - Math.min(start.getX(), end.getX());
        int _y = Math.max(start.getY(), end.getY()) - Math.min(start.getY(), end.getY());
        
        double _magnitude = Math.pow(_x, 2) + Math.pow(_y, 2);                
        
        return new UnitVector(_x/_magnitude, _y/_magnitude);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chord other = (Chord) obj;
        if (end == null)
        {
            if (other.end != null)
                return false;
        }
        else if (!end.equals(other.end))
            return false;
        if (start == null)
        {
            if (other.start != null)
                return false;
        }
        else if (!start.equals(other.start))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "[" + start.toString() + "->" + end.toString() + "]";
    }
    
    private boolean between(int aPoint, int aStart, int anEnd) {
        return (aPoint >= aStart-1) && (aPoint <= anEnd+1);
    }
}
