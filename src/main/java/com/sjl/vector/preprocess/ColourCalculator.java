package com.sjl.vector.preprocess;

import java.util.*;

public interface ColourCalculator {
    
    public void add(int aValue);        
    public int calculate();
    
    class MostFrequent implements ColourCalculator {
        int[] values = new int[256];
        
        public MostFrequent() {
            Arrays.fill(values, -1);
        }
        
        public void add(int aValue) {
            values[aValue] += 1;
        }
        
        public int calculate() {
            int _highest = -1;
            int _colour = -1;
            
            for (int i=0; i<256; i++) {
                if (values[i] > _highest) {
                    _highest = values[i];
                    _colour = i;
                }
            }
            
            return _colour;
        }
    }
    
    class SimpleAverage implements ColourCalculator {
        int[] values = new int[256];
        
        public SimpleAverage() {
            Arrays.fill(values, 0);
        }
        
        public void add(int aValue) {
            values[aValue] += 1;
        }
        
        public int calculate() {
            int _colour = -1;
            int _count = 0;
            for (int i=0; i<256; i++) {
                if (values[i] > 0) {
                    _colour += i;
                    _count++;
                }
            }
            
            return _colour/_count;
        }
    }
    
    class WeightedAverage implements ColourCalculator {
        int[] values = new int[256];
        
        public WeightedAverage() {
            Arrays.fill(values, 0);
        }
        
        public void add(int aValue) {            
            values[aValue] += 1;
        }
    
        public int calculate() {
            int _max = max();                        
            double _weight=0, _colour=0, _count=0;
            for (int i=0; i<256; i++) {       
                if (values[i] > 0) {                    
                    _weight = (((double)values[i]) / _max);                    
                    _weight = Math.pow(_weight, 5);                    
                    
                    _colour += _weight * i;
                    _count += _weight;
                }
            }
                    
            return (int) (_colour / _count);
        }
        
        private int max() {
            int _highest = 0;
            for (int i=0; i<256; i++) {
                if (values[i] > _highest) {
                    _highest = values[i];                    
                }
            }            
            return _highest;
        }
    }
}
