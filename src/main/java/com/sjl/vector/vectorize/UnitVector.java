package com.sjl.vector.vectorize;

public class UnitVector {
    
    private static final double ONE_RADIAN = 180 / Math.PI;
    
    private double x, y;
    
    public UnitVector(double anX, double aY) {
        x = anX;
        y = aY;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        UnitVector other = (UnitVector) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

    public int angleInDegrees(UnitVector aDirection) {
        double _x = aDirection.x;
        double _y = aDirection.y;
        
        return (int) (ONE_RADIAN * Math.acos(
            ((x*_x) + (y*_y) / (magnitude() * aDirection.magnitude())))
        );
    }
    
    public double magnitude() {
        return Math.sqrt((x*x) + (y*y));
    }
    
    public String toString() {
        return "|" + x + "," + y + "|";
    }    
}
