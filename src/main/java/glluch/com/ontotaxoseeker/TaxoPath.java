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

import com.glluch.findterms.TermsCount;
import com.glluch.utils.Out;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;


import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

/**
 * Do several actions on Path related to IEEE computers ontology.
 * @author Guillem LLuch Moll
 */
public class TaxoPath {

    
    protected ArrayList <String> noIEEEnouns= new ArrayList <>();
    
    /**
     * Search for all the paths in a txt document.
     * @param doc The txt to be processed
     * @return A list of paths and its ocurrences-
     * @throws IOException Reading files.
     */
    public  PathsCount findPaths(String doc) throws IOException{
       
         TermsCount nouns=this.nouns(doc);
        return this.findPaths(nouns);
    }
     
    /**
     * Given a text search for nouns and return it
     * @param doc The text source.
     * @return A list of nouns in doc and their counts.
     * @throws IOException Reading a file in freeling.
     */
    protected TermsCount nouns(String doc) throws IOException{
        //debug("TaxoPaths nouns:");
        TermsCount nouns;
        Terms terms = FreelingTagger.nouns(doc);
        //debug("terms");
        //debug(terms.pretyPrint());
        nouns = terms.toTermsCount();
        //debug("nouns");
        //debug(nouns.pretyPrint());
        return nouns;
    }
     
    /**
     * Given a list of terms (with counts) builds a list of path, with counts. So, 
     * transforms each term into null if it isn't in the taxonomy or in its path if 
     * it is in.
     * @param terms The list of terms and its counts.
     * @return A list of paths from the taxonomy and its counts.
     * @throws IOException Reading a file in freeling.
     */
    public PathsCount findPaths(TermsCount terms) throws IOException {
        PathsCount termsPaths = new PathsCount();
        Iterator termsIte=terms.iterator();
        //debug("PathsCount findPaths");
        while (termsIte.hasNext())
        {
            String key=(String) termsIte.next();
            String termUri=StringUtils.replace(key, " ", "_");
            termUri=Config.NS+termUri;
            Path termPath=this.path(termUri);
            //debug("termUri is "+termUri);
            if (termPath!=null){
            termsPaths.put(termPath,terms.get(key));
            //debug("\ttermPath is "+termPath.prettyPrint());
            }
            else {
                //debug("\tthere isn't in IEEE taxonomy");
            }
        }
        return termsPaths;

    }
     
    /**
     * Print in console the paths in friendly way.
     * @param termsPaths The list of term and its counts.
     */
    public void showPaths(PathsCount termsPaths) {
        Out.p(this.prettyPrintPaths(termsPaths));
    }
    
    /**
     * Creates a String ready for print.
     * @param termsPaths The list of term and its counts.
     * @return An String with a line per path and its ocurrences.
     */
    public String prettyPrintPaths(PathsCount termsPaths) {
        //debug("prettyPrintPaths");
        String s="";
        Iterator ite=termsPaths.iterator();
        while (ite.hasNext()){
           Path p=(Path) ite.next();
            s+=p.prettyPrint()+" -> "+termsPaths.get(p)+System.lineSeparator();
        }
        return s;
    }

    /**
     * Given a candite URI, search in the taxonomy and return its path, if the uri 
     * corresponds a term in the ontology or null otherwise.
     * @param termUri The candidate URI, for example http://glluch.com/ieee_taxonomy#routing . 
     * @return The path of the uri o null if the uri is missing. 
     * For example, /communications_technology/communication_systems/routing
     * @throws IOException Reading files.
     */
    public Path path(String termUri) throws IOException {

        Path p = new Path();
        if (!termUri.equals("")) {
            Resource lastObject;
            Statement lastS = this.up(termUri);
            if (lastS==null) return null;
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

    /**
     * Given a URI returns its immediate parent if exisists, null otherwise.
     * @param termUri The URI for which the parent will be searched.
     * @return An Apache Jena statement which corresponds to the parent. 
     * @throws IOException Reading the ontology file.
     */
    public Statement up(String termUri) throws IOException {
        //debug("TaxoPath.up, Searching for "+termUri);
        boolean found = false;
        Statement up = null;
        Resource r = Config.getModel().getResource(termUri);
        if (r!=null){
        StmtIterator statements = r.listProperties();
        while (!found && statements.hasNext()) {
            Statement next = statements.next();
            Property predicate = next.getPredicate();//is the relation URI

            if (predicate.getURI().equals(Config.WIDER)) {
                found = true;
                up = next; //is the Object URI

            }
        }
        }
        return up;
    }

      
    /*
    public PathsCount findTerms2(String doc) throws IOException {
        TermsCount res;
        //Vocabulary.filename = "resources/Terms.owl"; //optional
        FindTerms finder = new FindTerms();
        FindTerms.vocabulary = Vocabulary.get();
        //System.out.println(Vocabulary.listTerms());

        res = finder.foundAndCount(doc);
        
        if (res.isEmpty()){return null;}
        PathsCount termsPaths = this.findPaths(res);
        return termsPaths;

    }
    */
   

  

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
