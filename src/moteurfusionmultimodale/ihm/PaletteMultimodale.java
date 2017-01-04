/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteurfusionmultimodale.ihm;

import fr.irit.elipse.enseignement.isia.PaletteGraphique;

/**
 *
 * @author bouzekel
 */
public class PaletteMultimodale {
    private PaletteGraphique palette;
    
    public PaletteMultimodale(String adresse) {
        palette = new PaletteGraphique(adresse, 10, 10, 500, 550);
        palette.setVisible(true);
    }
}
