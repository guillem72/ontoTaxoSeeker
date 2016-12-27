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
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Statement;

/**
 * A wrapper for <a href="https://jena.apache.org/documentation/javadoc/jena/org/apache/jena/ontology/class-use/OntTools.Path.html">
 * org.apache.jena.ontology.OntTools.Path</a>.
 * @author Guillem LLuch Moll
 */
public class Path extends org.apache.jena.ontology.OntTools.Path {

    /**
     * Creates a friendly representation of the path.
     * @return An string showing the path like a unix file path. For example,
     * /electronic_design_automation_and_methodology/design_methodology/prototypes
     */
    public String prettyPrint() {
        String sp = "";
        ArrayList<String> nodes = new ArrayList<>();
        Iterator<Statement> states = this.iterator();
        while (states.hasNext()) {
            Statement st = states.next();
            String uri = st.getSubject().getURI();
            //String node=uri.substring(15);
            String node = StringUtils.removeStart(uri, Config.NS);
            nodes.add(node);
        }

        for (int i = nodes.size() - 1; i > -1; i--) {
            sp += "/" + nodes.get(i);
        }
        return sp;
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
