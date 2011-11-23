package com.sjl.vector.vectorize;

import java.util.*;

import org.jdom.*;

public class SVG {

    private int width, height;    
    private Map<Colour, ColourMap> maps;
    
    public SVG(int aWidth, int aHeight) {
        width = aWidth;
        height = aHeight;
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
        _root.setAttribute("width", "" + width);
        _root.setAttribute("height", "" + height);
        _root.setAttribute("viewBox", "0 0 " + width + " " + height);
        
        List<Shape> _shapes = new ArrayList<Shape>();
        for (ColourMap _cm : maps.values()) {
            _shapes.addAll(_cm.getShapes());
        }
        
        Collections.sort(_shapes);
        
        for (Shape _s : _shapes) {                
            Element _e = new Element("path", _ns);
            _e.setAttribute("stroke-width", "2");
            _e.setAttribute("stroke", _s.getColour().toString());
            _e.setAttribute("fill", _s.getColour().toString());
            _e.setAttribute("d", getCurves(_s));
            
            _root.addContent(_e);
        }
        
        return new Document(_root);        
    }
    
    private String getCurves(Shape aShape) {
        StringBuilder _sb = new StringBuilder();
        Chord _previous = null;
        for (Chord _current : aShape) {
            if (_previous != null) {
                curve(_sb, _previous, _current);
            } else {
                _sb.append(" M " + position(aShape.first().getStart()));
            }
            _previous = _current;
        }
        
        if (_previous != null)
            curve(_sb, _previous, aShape.first());
        
        _sb.append(" Z");
        
        return _sb.toString();
    }

    private void curve(StringBuilder _sb, Chord _previous, Chord _current)
    {
        _sb.append(" C ").
            append(position(_previous.getStart())).        
            append(position(_previous.getEnd())).
            append(position(_current.getStart()));        
    }
    
    private String position(Point aPoint) {
        return aPoint.getX() + " " + aPoint.getY() + " ";
    }
    
    public Document toPolyXML() {
        Namespace _ns = Namespace.getNamespace("http://www.w3.org/2000/svg");
        Element _root = new Element("svg", _ns);
        _root.setAttribute("width", "" + width);
        _root.setAttribute("height", "" + height);
        _root.setAttribute("viewBox", "0 0 " + width + " " + height);
        
        List<Shape> _shapes = new ArrayList<Shape>();
        for (ColourMap _cm : maps.values()) {
            _shapes.addAll(_cm.getShapes());
        }
        
        Collections.sort(_shapes);
        
        for (Shape _s : _shapes) {                
            Element _e = new Element("polygon", _ns);
            _e.setAttribute("style", "stroke:" + _s.getColour() + "; fill:" + _s.getColour());
            _e.setAttribute("points", getPoints(_s));
            
            _root.addContent(_e);
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
