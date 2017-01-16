/*
 * To change this license header, newchoose License Headers in Project Properties.
 * To change this template file, newchoose Tools | Templates
 * and open the template in the editor.
 */
package moteurfusionmultimodale;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            file = new FileOutputStream("Dictionnaire.txt", true);
            writer = new BufferedWriter(new OutputStreamWriter(file, "utf-8"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dictionnaire.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Dictionnaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        read();
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

    private void read() {
        //lecture du fichier texte
        String chaine = "";
        try {
            InputStream ips;
            ips = new FileInputStream("Dictionnaire.txt");
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                System.out.println(ligne);
                chaine += ligne;
            }
            String[] formes = chaine.split("next");
            String[] rect = formes[0].split(":");
            String[] elli = formes[1].split(":");
            Stroke rectangle = new Stroke();
            List<Point2D.Double> rect_points = new ArrayList<>();
            String[] points = rect[1].split(";");
            for (String p : points) {
                String[] xy = p.split(" ");
                Point2D.Double point2d = new Point2D.Double(Double.valueOf(xy[0]), Double.valueOf(xy[1]));
                rect_points.add(point2d);
                rectangle.addPoint(point2d);
            }

            Stroke ellipse = new Stroke();
            List<Point2D.Double> ellip_points = new ArrayList<>();
            points = elli[1].split(";");
            for (String p : points) {
                String[] xy = p.split(" ");
                Point2D.Double point2d = new Point2D.Double(Double.valueOf(xy[0]), Double.valueOf(xy[1]));
                ellip_points.add(point2d);
                ellipse.addPoint(point2d);
            }

            String[] suppr = formes[2].split(":");
            String[] deplac = formes[3].split(":");
            Stroke supprimer = new Stroke();
            List<Point2D.Double> suppr_points = new ArrayList<>();
            points = suppr[1].split(";");
            for (String p : points) {
                String[] xy = p.split(" ");
                Point2D.Double point2d = new Point2D.Double(Double.valueOf(xy[0]), Double.valueOf(xy[1]));
                suppr_points.add(point2d);
                supprimer.addPoint(point2d);
            }

            Stroke deplacer = new Stroke();
            List<Point2D.Double> deplac_points = new ArrayList<>();
            points = deplac[1].split(";");
            for (String p : points) {
                String[] xy = p.split(" ");
                Point2D.Double point2d = new Point2D.Double(Double.valueOf(xy[0]), Double.valueOf(xy[1]));
                deplac_points.add(point2d);
                deplacer.addPoint(point2d);
            }
            
            formes2D.put(rectangle, "rectangle");
            formes2D.put(ellipse, "ellipse");
            formes2D.put(supprimer, "supprimer");
            formes2D.put(deplacer, "deplacer");

            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
