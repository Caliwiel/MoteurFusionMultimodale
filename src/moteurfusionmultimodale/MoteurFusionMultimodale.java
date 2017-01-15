/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteurfusionmultimodale;

import fr.dgac.ivy.IvyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import moteurfusionmultimodale.ihm.PaletteMultimodale;

/**
 *
 * @author bouzekel
 */
public class MoteurFusionMultimodale {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PaletteMultimodale palette = new PaletteMultimodale("127.255.255.255:2010");
            AgentReconnaisseurGeste agentgeste = new AgentReconnaisseurGeste("AgentGeste", "J'ecoute les gestes", null);
            AgentReconnaisseurVocal agentvocal = new AgentReconnaisseurVocal("AgentVocal", "J'ecoute la voix", null, palette);
            AgentFusionMultimodale agentmuli = new AgentFusionMultimodale("gentMultiModal", "J'ecoute agent geste et vocal", null);
        } catch (IvyException ex) {
            Logger.getLogger(MoteurFusionMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
