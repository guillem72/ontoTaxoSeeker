/*
 * The MIT License
 *
 * Copyright 2016 Guillem LLuch Moll <guillem72@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package glluch.com.ontotaxoseeker;


import java.io.IOException;
import org.apache.jena.ontology.OntModel;

/**
 * Some configuration common to all classes.
 * @author Guillem LLuch Moll
 */
public class Config {

    
    public static final String ROOT = "http://glluch.com/ieee_taxonomy#ieee";

  
    public static final String WIDER="http://glluch.com/ieee_taxonomy#wide";
    public static final String NS="http://glluch.com/ieee_taxonomy#";   
    public static final boolean DEGUB=true;    
    public static final boolean VERBOSE=true;  
    protected static OntModel model;

    /**
     * Get the OntModel if it is set and build it otherwise.
     * @return the model from ieee_taxo_v2_rdf.owl o form a taxonomy similar to it.
     * @throws IOException Reading files.
     */
    public static OntModel getModel() throws IOException {
        if (Config.model!=null)
        return model;
        else 
        {
              String filename="resources/ieee_taxo_v2_rdf.owl";
               IO io=new IO();
            io.setFilename(filename);
             model = io.read();
        }
        return model;
    }

    /**
     * Build the model outside and, with this method, set it.
     * @param model The model that has to be used in this library.
     */
    public static void setModel(OntModel model) {
        Config.model = model;
    }
    
    
}
