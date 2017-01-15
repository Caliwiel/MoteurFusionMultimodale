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

    public AgentReconnaisseurVocal(String string, String string1, IvyApplicationListener il, PaletteMultimodale p) {
      
        System.out.println("AgentReconnaisseurVocal constructeur");
        
        //Initialisation de la palette
        palette = p;
        
        try {
            bus = new Ivy("Recepteur", "je reçoit", null);
            bus.start("127.255.255.255:2010");     
            bus.bindMsg("sra5 Text=(.*) ", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                   System.out.println("Ivy vocal receive");
                   System.out.println("Message : " + strings[0]);
                   
                   switch (strings[0]){
                       case "rectangle" :
                           //Dessiner un rectangle
                           //getPaletteMultimodale().dessinerRectangle();
                           getPaletteMultimodale().dessinerRectangle(getPaletteMultimodale().getLastPosition());
                           
                           break;
                       case "ellipse" :
                           //Dessiner une ellipse
                           //getPaletteMultimodale().dessinerEllipse();
                           getPaletteMultimodale().dessinerEllipse(getPaletteMultimodale().getLastPosition());
                           break;
                       case "ici" :
                           //Recuperer une position
                           getPaletteMultimodale().dessinerEllipse(getPaletteMultimodale().getLastPosition());
                           break;
                       case "rouge" :
                       case "bleu":
                       case "vert":
                       case "jaune":
                       case "noir":
                       case "blanc":
                           //Changer de couleur        
                           System.out.println("on passe ici");
                           getPaletteMultimodale().changerCouleurFondPoint(getPaletteMultimodale().getLastPosition(), strings[0]);
                           break;
                       case "deplacer":
                           //Déplacer
                           getPaletteMultimodale().deplacerFormePoint(getPaletteMultimodale().getLastPosition(),getPaletteMultimodale().getLastPosition());
                           break;
                       case "taille":
                           //Modifier la taille
                           getPaletteMultimodale().modifierTailleObjetPoint(getPaletteMultimodale().getLastPosition(),100, 100);
                           break;
                       case "ce rectangle":
                       case "cette ellipse":
                       case "cet objet":
                           getPaletteMultimodale().selectionnerObject();
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
    
    private PaletteMultimodale getPaletteMultimodale (){
        return palette;
    }
    public void setPaletteMultimodale (PaletteMultimodale p){
        palette = p;
    }
}
