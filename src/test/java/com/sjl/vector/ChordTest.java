package com.sjl.vector;

import org.junit.*;

import com.sjl.vector.vectorize.*;

public class ChordTest {

    @Test
    public void knowsIfOtherChordTouchesBelow() {
        Chord _c1 = new Chord(new Point(5,5), new Point(10,5));
        Chord _c2 = new Chord(new Point(10,6), new Point(15,6));
        
        Assert.assertTrue(_c1.touches(_c2));
    }
    
    @Test
    public void knowsIfOtherChordTouchesAbove() {
        Chord _c1 = new Chord(new Point(5,5), new Point(10,5));
        Chord _c2 = new Chord(new Point(10,6), new Point(15,6));
        
        Assert.assertTrue(_c2.touches(_c1));
    }
    
    @Test
    public void knowsIfOtherChordNotTouchesAbove() {
        Chord _c1 = new Chord(new Point(5,5), new Point(8,5));
        Chord _c2 = new Chord(new Point(5,7), new Point(8,7));
        
        Assert.assertFalse(_c2.touches(_c1));
    }
    
    @Test
    public void knowsIfOtherChordNotTouchesBelow() {
        Chord _c1 = new Chord(new Point(5,5), new Point(8,5));
        Chord _c2 = new Chord(new Point(5,7), new Point(8,7));
        
        Assert.assertFalse(_c1.touches(_c2));
    }
    
    @Test
    public void knowsIfOtherChordNotTouchesAboveOffset() {
        Chord _c1 = new Chord(new Point(5,5), new Point(8,5));
        Chord _c2 = new Chord(new Point(10,6), new Point(15,6));
        
        Assert.assertFalse(_c2.touches(_c1));
    }
    
    @Test
    public void knowsIfOtherChordNotTouchesBelowOffset() {
        Chord _c1 = new Chord(new Point(5,5), new Point(8,5));
        Chord _c2 = new Chord(new Point(10,6), new Point(15,6));
        
        Assert.assertFalse(_c1.touches(_c2));
    }
}
