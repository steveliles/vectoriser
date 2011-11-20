package com.sjl.vector.vectorize;

public class Pixel {
    
    private Colour colour;
    private Point point;
    
    public Pixel(Point aPoint, Colour aColour) {
        point = aPoint;
        colour = aColour;
    }
    
    public Point getPoint() {
        return point;
    }
    
    public Colour getColour() {
        return colour;
    }

    public boolean isSameColourAs(Pixel aPrevious) {
        if (aPrevious == null)
            return false;
        
        return colour.equals(aPrevious.getColour());
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((colour == null) ? 0 : colour.hashCode());
        result = prime * result + ((point == null) ? 0 : point.hashCode());
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
        Pixel other = (Pixel) obj;
        if (colour == null) {
            if (other.colour != null)
                return false;
        }
        else if (!colour.equals(other.colour))
            return false;
        if (point == null) {
            if (other.point != null)
                return false;
        }
        else if (!point.equals(other.point))
            return false;
        return true;
    }    
}
