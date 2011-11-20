package com.sjl.vector.vectorize;

public class Point {
    
    private int x, y;        
    
    public Point(int anX, int aY) {
        x = anX;
        y = aY;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
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
        Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
