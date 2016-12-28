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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillem LLuch Moll 
 */
public class TermsTest {
    protected static Terms ts=null;
    
    //The file was generate in FreelingTagger
    protected static final String CLASSFILE="resources/test/Terms.ser";
    
    public TermsTest() throws IOException, FileNotFoundException, ClassNotFoundException {
     if (ts==null){
         ts=(Terms)TestsGen.load(CLASSFILE);
     }
    }

    /**
     * Test of pretyPrint method, of class Terms.
     */
    /* 
    //Not working because of the order.
    @Test
    public void testPretyPrint() throws IOException {
        System.out.println("Terms.pretyPrint");
        Terms instance = TermsTest.ts;
        String expResult = FileUtils.readFileToString
    (new File("resources/test/termsPrettyPrintResult.txt"));
        String result = instance.pretyPrint();
        assertEquals(expResult, result);
        
    }
    */
    /**
     * Test of toTermsCount method, of class Terms.
     */
    @Test
    public void testToTermsCount() throws IOException, FileNotFoundException, ClassNotFoundException {
        System.out.println("Terms.toTermsCount");
        Terms instance = TermsTest.ts;
       //resources/test/TermsCount.ser
        TermsCount expResult = (TermsCount)TestsGen.load("resources/test/TermsCount.ser");
        TermsCount result = instance.toTermsCount();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of addOne method, of class Terms.
     */
    @Test
    public void testAddOne() {
        System.out.println("Terms.addOne new");
        Term t = new Term("friend","NN");
        Terms instance = TermsTest.ts;
        //resources/test/Terms2.ser
        int expResult = 1;
        int result = instance.addOne(t);
        assertEquals(expResult, result, 0);
        
    }
    
      /**
     * Test of addOne method, of class Terms.
     */
    @Test
    public void testAddOne2() {
        System.out.println("Terms.addOne to two");
        Term t = new Term("noun","NN");
        Terms instance = TermsTest.ts;
        //resources/test/Terms2.ser
        int expResult = 2;
        int result = instance.addOne(t);
        assertEquals(expResult, result, 0);
        
    }
    
    /**
     * Test of terms method, of class Terms.
     */
    @Test
    public void testTerms() throws IOException {
        System.out.println("Terms.terms");
        Terms instance = TermsTest.ts;        
        File file=new File("resources/test/testTermsResults.bin");
        byte[] v=FileUtils.readFileToByteArray(file);
        ArrayList<String> expResult = SerializationUtils.deserialize(v);
        ArrayList<Term> result = instance.terms();
        Iterator iter = result.iterator();
        ArrayList<String> lemas=new ArrayList<>();
        while (iter.hasNext()){
            Term next = (Term) iter.next();
            lemas.add(next.getLema());
        }
        Out.p(expResult);
        Out.p(lemas);
        Assert.assertArrayEquals(expResult.toArray(), lemas.toArray());
        
    }

    

    
}
