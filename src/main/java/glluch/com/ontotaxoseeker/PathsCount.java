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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * A wrapper for Map path -&lt; int. 
 * @author Guillem LLuch Moll
 */
public class PathsCount extends HashMap<Path,Integer> implements java.io.Serializable{

    /**
     * This method transform this object to a TermsCount.
     * @return pretty print presentation of the path (as string) -&lt; counts
     */
    public TermsCount conceptsCount(){
         Iterator ite=this.iterator();
         TermsCount cc=new TermsCount();
         while (ite.hasNext()){
             Path p=(Path) ite.next();
             if (p!=null)
             cc.put(p.prettyPrint(), this.get(p));
         }
         return cc;
     }
    
    public String prettyPrint(){
    return this.conceptsCount().pretyPrint();
    }
    
    /**
     * Method for obtain the keys of this object.
     * @return an arrayList containing only the keys.
     */
    public ArrayList <Path> keys(){
        ArrayList<Path> res=new ArrayList<>();
        Set keyset=this.keySet();
        for (Object key0:keyset){
            res.add((Path)key0);
        }
        
        return res;
        
   }
    
    
    
    public Iterator iterator(){
    ArrayList <Path> keys=this.keys();
    return keys.iterator();
    }
    
    
    
    protected transient boolean verbose = true;

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    protected transient boolean debug=true;

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
    protected void debug(String text){
        if (this.debug){
        Out.p(text);
        }
    }

}
