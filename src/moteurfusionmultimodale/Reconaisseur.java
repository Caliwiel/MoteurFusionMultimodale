/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteurfusionmultimodale;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author bouzekel
 */
public class Reconaisseur {

    private Dictionnaire dico;

    public Reconaisseur(Dictionnaire d) {
        dico = d;
    }

    public String reconnaitreForme(Stroke forme) {
        String nom = "";
        Map<Stroke, String> nom_formes = dico.getFormes2D();
        Map<String, Double> scores = new HashMap<>();

        Set<Stroke> formes = nom_formes.keySet();
        for (Stroke dico_forme : formes) {
            double somme_distance = 0;
            List<Point2D.Double> points = forme.getListePoint();
            int cpt = 0;
            for (Point2D p : dico_forme.getPoints()) {
                if (cpt < points.size()) {
                    somme_distance += points.get(cpt).distance(p);
                }
                cpt++;
            }
            scores.put(nom_formes.get(dico_forme), somme_distance);
        }

        Set<String> noms = scores.keySet();
        double s = 99999999.0;
        for (String n : noms) {
            if (scores.get(n) < s) {
                nom = n;
                s = scores.get(n);
            }
        }
        System.out.println("reconnaisseur a trouvé : " + nom);
        return nom;
    }

}
