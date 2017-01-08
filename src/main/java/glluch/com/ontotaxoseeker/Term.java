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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.upc.freeling.*;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.Individual;

/**
 * A class for store word, lemma and its related information.
 * @author Guillem LLuch Moll
 */
public class Term implements java.io.Serializable {

    protected String lema;
    protected String posTag;
    protected String uri;
    protected boolean isIEEETerm = false;
    
    /**
     * Constructor form freeling word.
     * @param w Freeling word.
     * @throws java.io.IOException
     */
    public Term(Word w) throws IOException  {
        this.lema = w.getLemma();
        this.posTag = w.getTag();
        this.uri = Term.buildUri(lema);
        boolean isIEEE = this.isEEEterm();
        //For debug
        //GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        //Gson gson = builder.create();
        //String tpj = gson.toJson(this);
        //FileUtils.writeStringToFile(new File("resources/test/"+this.lema+".json"), tpj);
        //For debug2
        //GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        //Gson gson = builder.create();
        //String tpj = gson.toJson(w);
        //FileUtils.writeStringToFile(new File("resources/test/"+this.lema+"FreeLing.json"), tpj);
    }

    public Term(String lemma, String tag){
        this.lema = lemma;
        this.posTag = tag;
        this.uri = Term.buildUri(lema);
    }
    /**
     * Test if the term is present in the IEEE computers taxonomy
     * @return true if the term is there, false otherwise.
     * @throws IOException Reading file.
     */
    public boolean isEEEterm() throws IOException {
        //debug("isEEEterm "+this.uri+" "+this.isIEEETerm);
        if (this.isIEEETerm) {
            return true;
        }
        //debug("check IEEE");
        Individual ind = Config.getModel().getIndividual(this.uri);
        this.isIEEETerm=(ind != null);
        return this.isIEEETerm;
    }

    /**
     * Create the possible uri from a piece of text 
     * @param term A String with the possible name.
     * @return a String with the candidate uri.
     */
    public static String buildUri(String term) {
        //TODO check if term is, perhaps, an uri.
        String res;
        res = term.trim().toLowerCase();
        res = StringUtils.replace(res, " ", "_");
        return Config.NS + res;
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

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public String getPosTag() {
        return posTag;
    }

    public void setPosTag(String posTag) {
        this.posTag = posTag;
    }

    public String getUri() {
        return uri;
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
