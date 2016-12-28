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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillem LLuch Moll <guillem72@gmail.com>
 */
public class FreelingTaggerTest {
    
    public FreelingTaggerTest() {
    }

    /**
     * Test of nouns method, of class FreelingTagger.
     */
    //@Test
    public void testNouns() throws Exception {
        //System.setProperty("java.library.path", "/opt/FreeLing-4.0/APIs/java/");
        System.out.println("FreelingTagger.nouns");
        String doc="Two terms: study routing, please, and recursive estimation. "
            + "PKI isn't there, but is a noun too.";
        String expResult = "noun -> 1\n" +
"term -> 1\n" +
"study -> 1\n" +
"estimation -> 1\n" +
"pki -> 1";
        String result = FreelingTagger.nouns(doc).pretyPrint();
        //assertEquals(expResult, result);
        
    }
    /*
    no freeling_javaAPI in java.library.path
java.lang.UnsatisfiedLinkError
     */
}
