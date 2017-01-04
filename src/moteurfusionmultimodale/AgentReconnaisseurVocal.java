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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bouzekel
 */
public class AgentReconnaisseurVocal {
    private Ivy bus;

    public AgentReconnaisseurVocal(String string, String string1, IvyApplicationListener il) {
      
        
        try {
            bus = new Ivy("Recepteur", "je reçoit", null);
            bus.start("127.255.255.255:2010");
            bus.bindMsg("sra5 Parsed=Action:deplacement Position:(.*) Confidence=(.*) NP=(.*) Num_A=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                   

                }

            });
            bus.bindMsg("sra5 Parsed=Action:initialiser Confidence=(.*) NP=(.*) Num_A=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    try {
                        System.out.println("OK init ");

                        bus.sendMsg("ppilot5 Say=\"Initialize\"");
                    } catch (IvyException ex) {
                        Logger.getLogger(AgentReconnaisseurVocal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });
        } catch (IvyException ex) {
            Logger.getLogger(AgentReconnaisseurVocal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
