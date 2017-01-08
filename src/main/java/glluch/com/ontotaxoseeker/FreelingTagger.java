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
import java.io.IOException;

import edu.upc.freeling.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.lang3.StringUtils;
/**
 * Class to do NLP natural language processing with freeling 4. 
 * @author Guillem LLuch Moll
 */
public class FreelingTagger {
          
  private static final String FREELINGDIR = "/usr/local";
  private static final String DATA = FREELINGDIR + "/share/freeling/";
  private static String LANG = "en";
  static MacoOptions op;
  static Tokenizer tk;
  static Splitter sp;
  static HmmTagger tg;
  static ChartParser parser;
  static  DepTxala dep;
  static Nec neclass;
  static Senses sen;
  static Ukb dis;

    /**
     * Analizes a doc and return only the terms that are nouns.
     * @param doc The text to be analized.
     * @return Terms, all the nouns terms in doc.
     * @throws IOException Reading files.
     */
    public static Terms nouns(String doc) throws IOException {
    if (StringUtils.isEmpty(doc)) {
        Out.p("FreelingTagger, nouns: doc is null");
            //throw new IllegalArgumentException("The argument cannot be null");
        Terms ts=new Terms();
        return ts;
    }
        System.loadLibrary( "freeling_javaAPI" );


    Util.initLocale( "default" );

    // Create options set for maco analyzer.
    // Default values are Ok, except for data files.
    if (null==op) {
        op = new MacoOptions( LANG );
    
    
    op.setDataFiles( "", 
                     DATA + "common/punct.dat",
                     DATA + LANG + "/dicc.src",
                     DATA + LANG + "/afixos.dat",
                     "",
                      "resources/testLocucionsEn.dat", 
                     DATA + LANG + "/np.dat",
                     DATA + LANG + "/quantities.dat",
                     DATA + LANG + "/probabilitats.dat");
    } 
    

    if(tk==null){ tk = new Tokenizer( DATA + LANG + "/tokenizer.dat" );}
    if (sp==null) sp = new Splitter( DATA + LANG + "/splitter.dat" );
    SWIGTYPE_p_splitter_status sid = sp.openSession();

    Maco mf = new Maco( op );
    mf.setActiveOptions(false, true, true, true,  // select which among created 
                        true, true, false, true,  // submodules are to be used. 
                        true, true, true, true);  // default: all created submodules 
                                                  // are used

    if (tg==null) tg = new HmmTagger( DATA + LANG + "/tagger.dat", true, 2 );
    if (parser==null ) parser = new ChartParser(
      DATA + LANG + "/chunker/grammar-chunk.dat" );
    if (dep==null) dep = new DepTxala( DATA + LANG + "/dep_txala/dependences.dat",
      parser.getStartSymbol() );
    if (neclass==null) neclass = new Nec( DATA + LANG + "/nerc/nec/nec-ab-poor1.dat" );

    if (sen==null) sen = new Senses(DATA + LANG + "/senses.dat" ); // sense dictionary
    if (dis==null) dis = new Ukb( DATA + LANG + "/ukb.dat" ); // sense disambiguator
      
      // Extract the tokens from the line of text.
      ListWord l = tk.tokenize( doc );

      // Split the tokens into distinct sentences.
      ListSentence ls = sp.split( sid, l, false );

      // Perform morphological analysis
      mf.analyze( ls );

      // Perform part-of-speech tagging.
      tg.analyze( ls );

      // Perform named entity (NE) classificiation.
      neclass.analyze( ls );

      //sen.analyze( ls );
      //dis.analyze( ls );
      Terms tagOnlyNouns = FreelingTagger.tagOnlyNouns(ls);
      //debug("DOC");
      //debug(doc);
      //debug("Result");
      //debug(tagOnlyNouns.pretyPrint());
      return tagOnlyNouns;  
    
  }

    /**
     * Given a freeling list of senteces processes it and return all the nouns in the list.
     * @param ls a freeling list of senteces.
     * @return Terms, all the nouns terms in the sentences.
     */ 
    protected static Terms tagOnlyNouns(ListSentence ls ) throws IOException{
      Terms terms=new Terms();
       //debug("freelingtager tagOnlyNouns");
       ListSentenceIterator sIt = new ListSentenceIterator(ls);
      while (sIt.hasNext()) {
        Sentence s = sIt.next();
        ListWordIterator wIt = new ListWordIterator(s);
        while (wIt.hasNext()) {
          Word w = wIt.next();
         //debug(w.getLemma()+" "+w.getTag());
          if (StringUtils.startsWith(w.getTag(), "N")){
         Term t=new Term(w);
         terms.addOne(t);
          }
          
        }

       
      }
      
      TestsGen.save(terms);
      
      return terms;
      
  } 
  
  
  private static void printResults( ListSentence ls, String format ) {

    
      System.out.println( "-------- TAGGER results -----------" );

      // get the analyzed words out of ls.
      ListSentenceIterator sIt = new ListSentenceIterator(ls);
      while (sIt.hasNext()) {
        Sentence s = sIt.next();
        ListWordIterator wIt = new ListWordIterator(s);
        while (wIt.hasNext()) {
          Word w = wIt.next();

          System.out.print(
            w.getForm() + " " + w.getLemma() + " " + w.getTag() );
         // printSenses( w );
          System.out.println();
        }

        System.out.println();
      }
    
  }
   /**
     * Sent a message to the console depens on the parametre verbose. If it is
     * true (on), the text is shown.
     *
     * @param text The text to be shown
     */
    protected static void show(String text) {
        if (Config.VERBOSE) {
            Out.p(text);
        }
    }

    /**
     * Sent a message to the console depens on the parametre debug. If it is
     * true (on), the text is shown.
     *
     * @param text The text to be shown
     */
    protected static void debug(String text) {
        if (Config.DEGUB) {
            Out.p(text);
        }
    }
  
}

