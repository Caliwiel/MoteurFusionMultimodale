/*
 * To change this license header, newchoose License Headers in Project Properties.
 * To change this template file, newchoose Tools | Templates
 * and open the template in the editor.
 */
package moteurfusionmultimodale;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bouzekel
 */
public class Dictionnaire {

    Map<Stroke, String> formes2D;
    FileOutputStream file;
    Writer writer;

    public Dictionnaire() {
        try {
            this.formes2D = new HashMap<>();
            file = new FileOutputStream("Dictionnaire.txt",true);
            writer = new BufferedWriter(new OutputStreamWriter(file, "utf-8"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dictionnaire.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Dictionnaire.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addToDictionnary(String nom, Stroke stroke) {
        formes2D.put(stroke, nom);

    }

    public Map<Stroke, String> getFormes2D() {
        return formes2D;
    }

    
    public void saveDico() throws IOException {
        Set<Stroke> strokes = formes2D.keySet();
        for (Stroke s : strokes) {
            writer.flush();
            writer.append(formes2D.get(s) + " : " + s.listePoint.toString());
        }
        
    }

}
