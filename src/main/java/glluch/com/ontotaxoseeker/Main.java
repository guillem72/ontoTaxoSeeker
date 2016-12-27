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

import com.glluch.utils.Out;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;

/**
 *
 * @author Guillem LLuch Moll
 */
public class Main {
     static final String NS="http://glluch.com/ieee_taxonomy#";
     static final String WIDER="http://glluch.com/ieee_taxonomy#wide";
     //http://glluch.com/ieee_taxonomy#narrowed
     //http://glluch.com/ieee_taxonomy#level
     //http://www.w3.org/2000/01/rdf-schema#label
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //String filename="resources/ieee_onto2_reasoning.rdf"; ieee_onto2_reasoning.owl
        //String filename="resources/ieee_onto2_reasoning.rdf";
        //String filename="resources/ieee_taxo_v2_rdf.owl";
        //OntModel ieee=model(filename);
        //Out.p(ieee.getBaseModel());
        //exploreIndividuals (ieee);
        //exploreClass(ieee);
        //exploreStatements(ieee);
        //projection_algorithms
        //routing
        
       
        //String term="routing";
        //String termUri=NS+term;
        //explorePath(ieee,termUri);
         //p.path(termUri);
        //String doc="Linux Evolution and Popular Operating Systems and cognitive radio";
        String doc="Interprets the application design to develop a suitable "
            + "application in accordance with customer needs. Adapts existing "
            + "solutions by e.g. porting an application to another operating "
            + "system. Codes, debugs, tests and documents and communicates "
            + "product development stages. Selects appropriate technical "
            + "options for development such as reusing, improving or "
            + "reconfiguration of existing components. Optimises efficiency, "
            + "cost and quality. Validates results with user representatives, "
            + "integrates and commissions the overall solution.Debugging";
         findPaths(doc);
        
    }
    
      public static void findPaths(String doc) throws IOException{
         Config.getModel();
        TaxoPath p=new TaxoPath();
        
         PathsCount paths = p.findPaths(doc);
        p.showPaths(paths);
    }
    
    
  
    
  
    
    protected static OntModel model(String filename) throws IOException{
        IO io=new IO();
        io.setFilename(filename);
        OntModel ieee = io.read();
        return ieee;
    }
    
    public static void explorePath(OntModel m,String termUri){
        
         boolean found=false;
        Resource r=m.getResource(termUri);
         StmtIterator statements = r.listProperties();
          while (!found && statements.hasNext() ){
            Statement next = statements.next();
            Property predicate = next.getPredicate();//is the relation URI
          
            if (predicate.getURI().equals(WIDER)){
                found=true;
                Resource up = next.getResource(); //is the Object URI
                Out.p(predicate);
                Out.p(up);
            }
            }
    }
    
    
    
    public static void exploreResource(OntModel m,String termUri){
        
        Resource r=m.getResource(termUri);
         StmtIterator statements = r.listProperties();
          while (statements.hasNext() ){
            Statement next = statements.next();
            Property predicate = next.getPredicate();//is the relation URI
            Resource resource = next.getResource(); //is the Object URI
            //Out.p(next);
            Out.p(predicate);
            Out.p(resource);
            Out.p();
            }
    }
    
    
    public static void exploreClass(OntModel m){
        ExtendedIterator<OntClass> listClasses = m.listClasses();
            while (listClasses.hasNext() ){
            OntClass c=listClasses.next();
            Out.p(c);
            }
    }
    
    public static void exploreIndividuals(OntModel m){
        ExtendedIterator<Individual> individuals = m.listIndividuals();
        int i=0;
        int max=5;
          while (individuals.hasNext() && i<max){
            Individual indi = individuals.next();
            Out.p(indi);
            //Out.p(indi.getModel()==m);
            i++;
          }
    }
    
      public static void exploreStatements(OntModel m){
        StmtIterator statements = m.listStatements();
        int i=0;
        int max=5;
          while (statements.hasNext() && i<max){
            Statement next = statements.next();
            Out.p(next.asTriple()); //error org.apache.jena.rdf.model.LiteralRequiredException
            //next.
            //Out.p(indi.getModel()==m);
            i++;
          }
    }
    //ieee.listStatements()
}
