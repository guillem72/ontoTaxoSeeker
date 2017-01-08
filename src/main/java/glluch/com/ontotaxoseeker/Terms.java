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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * A class for save a list of terms and its counts. 
 * @author Guillem LLuch Moll
 */
public class Terms extends HashMap<Term, Integer> implements java.io.Serializable {

   /**
     * To populated this list, use IEEEtermsCount() 
     */
    protected ArrayList<Term> ieeeTerms = new ArrayList<>();

    /**
     * To populated it, use IEEEtermsCount() 
     */
    protected ArrayList<Term> noIeeeTerms = new ArrayList<>();

    /**
     * Build a friendly representation of this object.
     * @return an String ready to print. 
     */
    public String pretyPrint(){
       String res="";
       Iterator iter=this.iterator();
       while (iter.hasNext()){
           Term term = (Term) iter.next();
           res+=term.getLema()+" -> "+this.get(term)+System.lineSeparator();
       }
       return res;
   }
    
    /**
     * Split the terms between terms in IEEE computers taxonomy and terms not 
     * present in the taxonomy. 
     * @return The list of terms in the IEEE computers taxonomy and its counts. 
     * Note that this method populated an arraylist with the terms not in 
     * the taxonomy.
     * @throws IOException Readin a file
     */
    protected TermsCount IEEEtermsCount() throws IOException {
        TermsCount tc = new TermsCount();
        Iterator iter = this.iterator();
        while (iter.hasNext()) {
            Term t = (Term) iter.next();
            int val = this.get(t);
            if (t.isEEEterm()) {
                this.ieeeTerms.add(t);
                tc.put(t.getLema(), val);
            }
            else {
                this.noIeeeTerms.add(t);
            }
        }
        return tc;
    }

    /**
     * Transform this terms to a string -&lt; count
     * @return string -&lt; count from this terms.
     */
    public TermsCount toTermsCount() {
        TermsCount tc = new TermsCount();
        //debug("Terms toTermsCount");
        Iterator iter = this.iterator();
        while (iter.hasNext()) {
            Term t = (Term) iter.next();
           
            int val = this.get(t);
            // debug("term "+t.getLema()+" -> "+val);
            tc.put(t.getLema(), val);
        }
        return tc;
    }

    protected Term containsLema(String lema){
        
        Iterator iter = this.iterator();
        while (iter.hasNext()){
            Term term = (Term) iter.next();
            if (StringUtils.isNotEmpty(lema) && StringUtils.equals(lema, term.getLema())){
               return term; 
            }
              
        }
            
    return null;
    }
    
    /**
     * Add one term to the list.
     * @param t The term to be add to this list.
     * @return the actual count of the param term .
     */
    public int addOne(Term t) {
        int val = 0;
        String lema=t.getLema();
        //debug("Terms.addOne, received: "+lema);
        
        Term t2=this.containsLema(lema);
        if (t2!=null) {
          //  debug("is there");
            val = this.get(t2);

        }
        else t2=t;
        val = val + 1;
        this.put(t2, val);
        //debug(this.pretyPrint());
        return this.get(t2);
    }

    /**
     * Method for obtain the keys of this object.
     * @return an arrayList containing only the keys.
     */
    public ArrayList<Term> terms() {
        ArrayList<Term> res = new ArrayList<>();
        Set keyset = this.keySet();
        for (Object key0 : keyset) {
            res.add((Term) key0);
        }

        return res;

    }
    
    /*
    public ArrayList<String> lemas(){
        Iterator iter = this.iterator();
        ArrayList<String> lemas=new ArrayList<>();
        while (iter.hasNext()){
            Term next = (Term) iter.next();
            lemas.add(next.getLema());
        }
        return lemas;
    }
    */
    public Iterator iterator() {
        ArrayList<Term> keys = this.terms();
        return keys.iterator();
    }

    public ArrayList<Term> getIeeeTerms() {
        return ieeeTerms;
    }

    public ArrayList<Term> getNoIeeeTerms() {
        return noIeeeTerms;
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
