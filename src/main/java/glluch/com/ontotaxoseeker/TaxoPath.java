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

import com.glluch.findterms.FindTerms;
import com.glluch.findterms.Vocabulary;
import com.glluch.utils.Out;
import static glluch.com.ontotaxoseeker.Main.WIDER;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

import org.apache.jena.ontology.OntModel;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

/**
 *
 * @author Guillem LLuch Moll
 */
public class TaxoPath {

    protected OntModel model;
    
    
    

    public ArrayList<Path> findTerms(String doc) {
        ArrayList<String> res;
        //Vocabulary.filename = "resources/Terms.owl"; //optional
        FindTerms finder = new FindTerms();
        FindTerms.vocabulary = Vocabulary.get();
        //System.out.println(Vocabulary.listTerms());

        res = finder.found(doc);
        
        if (res.isEmpty()){return null;}
        ArrayList<Path> termsPaths = this.findPaths(res);
        return termsPaths;

    }

    public void showPaths(ArrayList<Path> termsPaths) {
        for (Path p : termsPaths) {
            Out.p(p.prettyPrint());
        }
    }
    
    public String prettyPrintPaths(ArrayList<Path> termsPaths) {
        String s="";
        for (Path p : termsPaths) {
            s+=p.prettyPrint()+"\n";
        }
        return s;
    }

    public ArrayList<Path> findPaths(ArrayList<String> terms) {
        ArrayList<Path> termsPaths = new ArrayList<>();
        for (String term : terms) {
            String termUri=StringUtils.replace(term, " ", "_");
            termUri=Config.NS+termUri;
            termsPaths.add(this.path(termUri));
        }
        return termsPaths;

    }

    public Path path(String termUri) {

        Path p = new Path();
        if (termUri != "") {
            Resource lastObject;
            Statement lastS = this.up(termUri);
            lastObject = (Resource) lastS.getObject();
            //debug(termUri);
            //debug("Subject:" + lastS.getSubject().getURI());
            //debug("Object:" + lastS.getObject());

            while (!lastObject.getURI().equals(Config.ROOT)) {
                boolean added = p.add(lastS);
                if (!added) {
                    show(lastS + " can't be added");
                }
                lastObject = (Resource) lastS.getObject();
                termUri = lastObject.getURI();
                lastS = this.up(termUri);

            }
        }

        //Out.p(p.prettyPrint());
        return p;
    }

    public Statement up(String termUri) {
        //debug("TaxoPath.up, Searching for "+termUri);
        boolean found = false;
        Statement up = null;
        Resource r = model.getResource(termUri);
        StmtIterator statements = r.listProperties();
        while (!found && statements.hasNext()) {
            Statement next = statements.next();
            Property predicate = next.getPredicate();//is the relation URI

            if (predicate.getURI().equals(Config.WIDER)) {
                found = true;
                up = next; //is the Object URI

            }
        }
        return up;
    }

    public OntModel getModel() {
        return model;
    }

    public void setModel(OntModel model) {
        this.model = model;
    }

  

    protected transient boolean verbose = true;

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    protected transient boolean debug = true;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Sent a message to the console depens on the parametre verbose. If it is
     * true (on), the text is shown.
     *
     * @param text The text to be shown
     */
    protected void show(String text) {
        if (this.verbose) {
            Out.p(text);
        }
    }

    /**
     * Sent a message to the console depens on the parametre debug. If it is
     * true (on), the text is shown.
     *
     * @param text The text to be shown
     */
    protected void debug(String text) {
        if (this.debug) {
            Out.p(text);
        }
    }

}
