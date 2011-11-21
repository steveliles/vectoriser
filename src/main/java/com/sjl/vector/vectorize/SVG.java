package com.sjl.vector.vectorize;

import java.util.*;

import org.jdom.*;

public class SVG {

    private Map<Colour, ColourMap> maps;
    
    public SVG() {
        maps = new HashMap<Colour, ColourMap>();
    }
    
    public void addChord(Point aFrom, Point aTo, Colour aColour) {
        ColourMap _m = getColourMap(aColour);
        _m.addChord(new Chord(aFrom, aTo));
    }    
    
    public ColourMap getColourMap(Colour aColour) {
        ColourMap _m = maps.get(aColour);
        if (_m == null) {
            _m = new ColourMapImpl(aColour);       
            maps.put(aColour, _m);
        }
        return _m;
    }           
    
    public Document toXML() {
        Namespace _ns = Namespace.getNamespace("http://www.w3.org/2000/svg");
        Element _root = new Element("svg", _ns);
        
        for (ColourMap _cm : maps.values()) {
            for (Shape _s : _cm.getShapes()) {                
                Element _e = new Element("polygon", _ns);
                _e.setAttribute("style", "stroke:" + _s.getColour() + "; fill:" + _s.getColour());
                _e.setAttribute("points", getPoints(_s));
                
                _root.addContent(_e);
            }
        }
        
        return new Document(_root);        
    }
    
    private String getPoints(Shape aShape) {
        StringBuilder _sb = new StringBuilder();
        Point _last = null;
        for (Chord _c : aShape.getChords()) {
            if (!_c.getStart().equals(_last)) {            
                _sb.append(_c.getStart().getX()).append(",");
                _sb.append(_c.getStart().getY()).append("  ");
            }
            
            _sb.append(_c.getEnd().getX()).append(",");
            _sb.append(_c.getEnd().getY()).append("  ");
            
            _last = _c.getEnd();
        }
        return _sb.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder _sb = new StringBuilder();
        for (ColourMap _cm : maps.values()) {
            for (Shape _s : _cm.getShapes()) {                
                _sb.append(_s.toString()).append("\r\n");
            }
        }
        return _sb.toString();
    }
}
