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
import java.awt.geom.Point2D;

/**
 *
 * @author bouzekel
 */
public class AgentFusionMultimodale {
    private Ivy bus;
    
    private StateMachine machine;
    
    public AgentFusionMultimodale(String string, String string1, IvyApplicationListener il, StateMachine m) throws IvyException {
        machine = m;
        bus = new Ivy(string, string1, il);
        bus.start("127.255.255.255:2010");
        
        bus.bindMsg("FusionMultimodale:(.*)", new IvyMessageListener() {
            @Override
            public void receive(IvyClient ic, String[] strings) {
                
                switch(strings[0]) {
                    case "rectangle" : 
                        System.out.println("Syso rectangle");
                        machine.handleGestEvent(GesteEvent.RECTANGLE);
                        break;
                    case "ellipse":
                        machine.handleGestEvent(GesteEvent.ELLIPSE);
                        break;
                    case "deplacer":
                        machine.handleGestEvent(GesteEvent.DEPLACER);
                        break;
                    case "supprimer":
                        machine.handleGestEvent(GesteEvent.SUPPRIMER);
                        break;
                }
                
            }

        });
    }
}
