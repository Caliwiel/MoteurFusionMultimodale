/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteurfusionmultimodale;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import moteurfusionmultimodale.ihm.PaletteMultimodale;

/**
 *
 * @author bouzekel
 */
public class AgentReconnaisseurVocal {
    private Ivy bus;
    private PaletteMultimodale palette;
    
    private StateMachine machine;

    public AgentReconnaisseurVocal(String string, String string1, IvyApplicationListener il, StateMachine m) {
        machine = m;
        System.out.println("AgentReconnaisseurVocal constructeur");
        
        //Initialisation de la palette
       // palette = p;
        
        try {
            bus = new Ivy("Recepteur", "je reçoit", null);
            bus.start("127.255.255.255:2010");     
            bus.bindMsg("sra5 Text=(.*) ", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                   //System.out.println("Ivy vocal receive");
                   //System.out.println("Message : " + strings[0]);
                   
                   switch (strings[0]){
                       case "ici" :
                           m.handleVocalEvent(VocalEvent.ICI);
                           break;
                       case "la" :
                           m.handleVocalEvent(VocalEvent.LA);
                           break;
                       case "position" :
                           m.handleVocalEvent(VocalEvent.POSITION);
                           break;
                       case "rouge" :
                           m.handleVocalEvent(VocalEvent.ROUGE);
                           break;
                       case "bleu":
                           m.handleVocalEvent(VocalEvent.BLEU);
                           break;
                       case "vert":
                           m.handleVocalEvent(VocalEvent.VERT);
                           break;
                       case "jaune":
                           m.handleVocalEvent(VocalEvent.JAUNE);
                           break;
                       case "noir":
                           m.handleVocalEvent(VocalEvent.NOIR);
                           break;
                       case "blanc":
                           m.handleVocalEvent(VocalEvent.BLANC);
                           break;
                       case "ce rectangle":
                           m.handleVocalEvent(VocalEvent.CE_RECTANGLE);
                           break;
                       case "cette ellipse":
                           m.handleVocalEvent(VocalEvent.CETTE_ELLIPSE);
                           break;
                       case "cet objet":
                           m.handleVocalEvent(VocalEvent.CET_OBJET);
                           break;
                       default :
                           break;
                   }
                }

            });            
        } catch (IvyException ex) {
            Logger.getLogger(AgentReconnaisseurVocal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
