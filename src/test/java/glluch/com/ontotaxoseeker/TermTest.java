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

import com.google.gson.Gson;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillem LLuch Moll <guillem72@gmail.com>
 */
public class TermTest {
    
    protected static final Term ieeeTerm=new Term("estimation","NN");
    protected static final Term noIeeeTerm=new Term("pki","NP00O00");
    public TermTest() {
        
    }

    /**
     * Test of isEEEterm method, of class Term.
     * @throws java.lang.Exception
     */
    @Test
    public void testIsEEEterm() throws Exception {
        System.out.println("Term.isEEEterm non");
        Term instance = TermTest.noIeeeTerm;
        boolean expResult = false;
        boolean result = instance.isEEEterm();
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of isEEEterm method, of class Term.
     * @throws java.lang.Exception
     */
    @Test
    public void testIsEEEterm2() throws Exception {
        System.out.println("Term.isEEEterm yes");
        Term instance = TermTest.ieeeTerm;
        boolean expResult = true;
        boolean result = instance.isEEEterm();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of buildUri method, of class Term.
     */
    @Test
    public void testBuildUri() {
        System.out.println("Term.buildUri");
        String term = "estimation";
        String expResult = "http://glluch.com/ieee_taxonomy#estimation";
        String result = Term.buildUri(term);
        assertEquals(expResult, result);
        
    }

   
    


    

    
}
