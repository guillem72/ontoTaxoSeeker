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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Class that generates results for tests.
 * @author Guillem LLuch Moll
 */
public class TestsGen {
    
    public static final String DOC="Two terms: study routing, please, and recursive estimation. "
            + "PKI isn't there, but is a noun too.";
    
    public static  String save(Object o, String className) throws FileNotFoundException, IOException{
        
       
        if (StringUtils.isEmpty(className)){
            Out.p("TestsGen.save have received an anonomous object");
            className="anonimousClass";
        }
        
        FileOutputStream fileOut =  
            new FileOutputStream("resources/test/"+className+".ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(o);
         out.close();
         fileOut.close();
         System.out.printf("Serialized data is saved in "
             + "resources/test/"+className+".ser"+System.lineSeparator());
         
         return "resources/test/"+className+".ser";
    }
    
    public static  String save(Object o) throws IOException{
        String className=o.getClass().getSimpleName();
        return save(o,className);
    }
    
    /**
     * Deserialize an object
     * @param filename The file name where the object was serialized.
     * @return An object. Most likely a posterior cast will be needed.
     * @throws FileNotFoundException if the ser file was not found
     * @throws IOException ?
     * @throws ClassNotFoundException Very unlikely to appear. The object class is always there.
     */
    public static Object load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException{
        
        FileInputStream fileIn = new FileInputStream(filename); 
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object o = in.readObject();
       
             return o;
    }
    
    public void testTermsResults() throws IOException, FileNotFoundException, ClassNotFoundException{
        Terms ts=(Terms)TestsGen.load("resources/test/Terms.ser");
        ArrayList<Term> terms = ts.terms();
         Iterator iter = terms.iterator();
        ArrayList<String> lemas=new ArrayList<>();
        while (iter.hasNext()){
            Term next = (Term) iter.next();
            lemas.add(next.getLema());
        }
        File target=new File("resources/test/testTermsResults.bin");
        byte[] vs=SerializationUtils.serialize(lemas);
        FileUtils.writeByteArrayToFile(target, vs);       
    }
        
   public void testAddOneResults() throws IOException, FileNotFoundException, ClassNotFoundException{
       Terms ts=(Terms)TestsGen.load("resources/test/Terms.ser");
       Term t=new Term("noun","NN");
       ts.addOne(t);
       Out.p(ts.pretyPrint());
       save(t,"Terms2");
   }
    
    public void testToTermsCountResults() throws IOException, FileNotFoundException, ClassNotFoundException{
        Terms ts=(Terms)TestsGen.load("resources/test/Terms.ser");
        TermsCount result = ts.toTermsCount();
        TestsGen.save(result);
    }
    
    
    public void termsPrettyPrintResult() throws IOException, FileNotFoundException, ClassNotFoundException{
        Terms ts=(Terms)TestsGen.load("resources/test/Terms.ser");
        String pp=ts.pretyPrint();
        
        FileUtils.writeStringToFile(new File("resources/test/termsPrettyPrintResult.txt"), pp);
    }
    
    
}
