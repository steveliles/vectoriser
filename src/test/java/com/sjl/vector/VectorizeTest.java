package com.sjl.vector;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.junit.*;

import com.sjl.vector.vectorize.*;

public class VectorizeTest {

    @Before
    public void setup() {
        System.out.println("---");
    }
    
    @Test
    public void writeOneToTheDesktop()
    throws Exception {
         Vectorizer _v = new Vectorizer();
         SVG _result = _v.vectorize(ImageIO.read(new File("/home/steve/Desktop/pre-processed-duck.png")));
         XMLOutputter _out = new XMLOutputter(Format.getPrettyFormat());
         
        _out.output(_result.toXML(), new FileWriter(new File("/home/steve/Desktop/test.txt")));
    }
    
    @Test
    public void vectorizesPlainCanvasToSinglePolygon()
    throws Exception {
         testVectorisation("svg-test-1.png", "svg-test-result-1.svg");
    }
    
    @Test
    public void vectorizesSeparateSimplePolygons()
    throws Exception {
         testVectorisation("svg-test-2.png", "svg-test-result-2.svg");
    }
    
    @Test
    public void vectorizesSimpleNestedPolygons()
    throws Exception {
         testVectorisation("svg-test-3.png", "svg-test-result-3.svg");
    }
    
    @Test
    public void vectorizesNestedPolygonsWithRepeatColours()
    throws Exception {
         testVectorisation("svg-test-4.png", "svg-test-result-4.svg");
    }
    
    @Test
    public void vectorizesComplexPolygons()
    throws Exception {
         testVectorisation("svg-test-5.png", "svg-test-result-5.svg");
    }
    
    private void testVectorisation(String aBitmapName, String anSVGName)
    throws Exception {
         Vectorizer _v = new Vectorizer();
         SVG _result = _v.vectorize(loadTestImage(aBitmapName));
         
         System.out.println(_result);
         
         XMLOutputter _out = new XMLOutputter(Format.getPrettyFormat());
                  
         Assert.assertEquals(
             loadExpectedSVGAsString(anSVGName), 
             _out.outputString(_result.toXML())
         );
    }
    
    private String loadExpectedSVGAsString(String aName) 
    throws Exception {
        SAXBuilder _builder = new SAXBuilder();
        Document _d = _builder.build(getClass().getResourceAsStream(aName));
        XMLOutputter _out = new XMLOutputter(Format.getPrettyFormat());
        return _out.outputString(_d);
    }
    
    private BufferedImage loadTestImage(String aName) 
    throws Exception {        
        return ImageIO.read(getClass().getResource(aName));        
    }
}
