package com.sjl.vector;

import org.junit.*;

import com.sjl.vector.vectorize.*;

public class UnitVectorTest {

    @Test
    public void calculates90DegreeAnglesWithOtherUnitVectors() {
        UnitVector _v1 = new UnitVector(1d,0d);
        UnitVector _v2 = new UnitVector(0d,1d);
        
        Assert.assertEquals(90, _v1.angleInDegrees(_v2));
    }
    
    @Test
    public void calculates0DegreeAnglesWithOtherUnitVectors() {
        UnitVector _v1 = new UnitVector(1d,0d);
        UnitVector _v2 = new UnitVector(1d,0d);
        
        Assert.assertEquals(0, _v1.angleInDegrees(_v2));
    }
    
    @Test
    public void calculates45DegreeAnglesWithOtherUnitVectors() {
        UnitVector _v1 = new UnitVector(0.707d,0.707d);
        UnitVector _v2 = new UnitVector(0d,1d);
        
        Assert.assertEquals(45, _v1.angleInDegrees(_v2));
    }
    
    @Test
    public void calculatesAnglesWithOtherUnitVectors() {
        UnitVector _v1 = new UnitVector(0.4d, 0.2d);        
        
        Assert.assertEquals(0, _v1.angleInDegrees(_v1));
    }
    
}
