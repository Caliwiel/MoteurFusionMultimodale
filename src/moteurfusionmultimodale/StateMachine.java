/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteurfusionmultimodale;


import fr.dgac.ivy.IvyException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import moteurfusionmultimodale.ihm.PaletteMultimodale;

/**
 *
 * @author cannacan
 */
public class StateMachine {
    private State currentState;
    private PaletteMultimodale palette;
    
    
    private Timer tCreerForme;
    private Timer tAjouterCouleur;
    private Timer tAjouterPosition;
    private Timer tSupprimer;
    private Timer tSupprimerSelect;
    private Timer tDeplacer;
    private Timer tDeplacerSelectObject;
    
    private VocalEvent vocalEvent;
    private GesteEvent gesteEvent;
    
    private String couleur = "";
    private Point position = new Point();
    
    private String nomObjet = "";
    
    public StateMachine(){
        currentState = State.INIT;
        try {
            setPaletteMultimodale(new PaletteMultimodale("127.255.255.255:2010"));
        } catch (IvyException ex) {
            Logger.getLogger(StateMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
        tCreerForme = new Timer (3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(State.INIT);
                tCreerForme.stop();
                //A5
                drawForme();
            }
        });
        tAjouterCouleur = new Timer (3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(State.INIT);
                tAjouterCouleur.stop();
                //A6
                drawFormeCouleur();
            }
        });
        tAjouterPosition = new Timer (3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(State.INIT);
                tAjouterPosition.stop();
                //A7
                drawFormePosition();
            }
        });
        tSupprimer = new Timer (3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(State.INIT);
                tSupprimer.stop();
            }
        });
        tSupprimerSelect = new Timer (3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(State.INIT);
                tSupprimerSelect.stop();
                //A11
                deleteObjet();
            }
        });
        tDeplacer = new Timer (3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(State.INIT);
                tDeplacer.stop();
            }
        });
        tDeplacerSelectObject = new Timer (3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(State.INIT);
                tDeplacerSelectObject.stop();
            }
        });
    }
    
    public void setState(State s){
        System.out.println("Nouveau state = " + s);
        this.currentState = s;
    }
    public State getState(){
        return this.currentState;
    }
    
    public void handleGestEvent (GesteEvent ge){
        switch (currentState){
            case INIT:
                if (ge == GesteEvent.RECTANGLE || ge == GesteEvent.ELLIPSE){
                    setState(State.CREERFORME);
                    tCreerForme.start();
                    //A1
                    setForme(ge);                 
                }
                if (ge == GesteEvent.DEPLACER){
                    setState(State.DEPLACER);
                    tDeplacer.start();               
                }
                if (ge == GesteEvent.SUPPRIMER){
                    setState(State.SUPPRIMER);
                    tSupprimer.start();               
                }
                break;
            case CREERFORME:
                //interdit
                break;
            case CREERFORMECOULEUR:
                //interdit
                break;
            case CREERFORMEPOSITION:
                //interdit
                break;
            case SUPPRIMER:
                //interdit
                break;
            case SUPPRIMERSELECTOBJECT:
                //interdit
                break;
            case DEPLACER:
                //interdit
                break;
            case DEPLACERPOSITION:
                //interdit
                break;
            case DEPLACEROBJECT: 
                //interdit
                break;
        }
    }
    
    public void handleVocalEvent(VocalEvent ve){
        switch (currentState){
            case INIT:
                //interdit
                break;
            case CREERFORME:
                //Position C3
                if (ve == VocalEvent.ICI || ve == VocalEvent.LA || ve == VocalEvent.POSITION) {
                    setState(State.CREERFORMEPOSITION);
                    tCreerForme.stop();
                    tAjouterPosition.start();
                    //A2
                    setPosition();
                }
                //Couleur C4
                if (ve == VocalEvent.BLANC || ve == VocalEvent.BLEU || ve == VocalEvent.JAUNE || ve == VocalEvent.NOIR || ve == VocalEvent.ROUGE || ve == VocalEvent.VERT ) {
                    setState(State.CREERFORMECOULEUR);
                    tCreerForme.stop();
                    tAjouterCouleur.start();
                    //A3
                    setCouleur(ve);
                }
                break;
            case CREERFORMECOULEUR:
                //Position C3
                if (ve == VocalEvent.ICI || ve == VocalEvent.LA || ve == VocalEvent.POSITION) {
                    setState(State.INIT);
                    tAjouterCouleur.stop();
                    //A2
                    setPosition();
                    //A8
                    drawFormeCouleurPosition();
                }
                break;
            case CREERFORMEPOSITION:
                if (ve == VocalEvent.BLANC || ve == VocalEvent.BLEU || ve == VocalEvent.JAUNE || ve == VocalEvent.NOIR || ve == VocalEvent.ROUGE || ve == VocalEvent.VERT ) {
                    setState(State.INIT);
                    tAjouterPosition.stop();
                    //A3
                    setCouleur(ve);
                    //A8
                    drawFormeCouleurPosition();
                }
                break;
            case SUPPRIMER:
                //Selection objet C5
                if (ve == VocalEvent.CETTE_ELLIPSE || ve == VocalEvent.CET_OBJET || ve == VocalEvent.CE_RECTANGLE){
                    setState(State.SUPPRIMERSELECTOBJECT);
                    tSupprimer.stop();
                    tSupprimerSelect.start();
                    //A9
                    setObjet();
                }
                break;
            case SUPPRIMERSELECTOBJECT:
                if (ve == VocalEvent.BLANC || ve == VocalEvent.BLEU || ve == VocalEvent.JAUNE || ve == VocalEvent.NOIR || ve == VocalEvent.ROUGE || ve == VocalEvent.VERT ) {
                    setState(State.INIT);
                    tSupprimerSelect.stop();
                    //A3
                    setCouleur(ve);
                    //A11
                    deleteObjectColor();
                }
                break;
            case DEPLACER:
                //Position C3
                if (ve == VocalEvent.ICI || ve == VocalEvent.LA || ve == VocalEvent.POSITION) {
                    setState(State.DEPLACERPOSITION);
                    tDeplacer.stop();
                    tDeplacerSelectObject.start();
                    //A2
                    setPosition();
                }
                //Selection objet C5
                if (ve == VocalEvent.CETTE_ELLIPSE || ve == VocalEvent.CET_OBJET || ve == VocalEvent.CE_RECTANGLE){
                    setState(State.DEPLACEROBJECT);
                    tDeplacer.stop();
                    tDeplacerSelectObject.start();
                    //A9
                    setObjet();
                }
                break;
            case DEPLACERPOSITION:
                //Selection objet C5
                if (ve == VocalEvent.CETTE_ELLIPSE || ve == VocalEvent.CET_OBJET || ve == VocalEvent.CE_RECTANGLE){
                    setState(State.INIT);
                    tDeplacerSelectObject.stop();
                    //A9
                    setObjet();
                    //A12
                    deplacer();
                }
                break;
            case DEPLACEROBJECT: //Position C3
                if (ve == VocalEvent.ICI || ve == VocalEvent.LA || ve == VocalEvent.POSITION) {
                    setState(State.INIT);
                    tDeplacerSelectObject.stop();
                    //A2
                    setPosition();
                    //A12
                    deplacer();
                }
                break;
        }
    }
    
    private PaletteMultimodale getPaletteMultimodale (){
        return palette;
    }
    public void setPaletteMultimodale (PaletteMultimodale p){
        palette = p;
    }
    
    //A1
    private void setForme (GesteEvent ge) {
        gesteEvent = ge;
    }
    //A2
    private void setPosition(){
        position = getPaletteMultimodale().getLastPosition();
    }
    
    //A3
    private void setCouleur (VocalEvent ve){
        switch (ve){
            case BLANC:
                this.couleur = "white";
                break;
            case BLEU:
                this.couleur = "blue";
                break;
            case JAUNE:
                this.couleur = "yellow";
                break;
            case NOIR:
                this.couleur = "black";
                break;
            case ROUGE:
                this.couleur = "red";
                break;
            case VERT:
                this.couleur = "green";
                break;
        }
        System.out.println("couleur : " + couleur);
    }
    //A5
    private void drawForme(){
        if (gesteEvent == GesteEvent.RECTANGLE){
            getPaletteMultimodale().dessinerRectangle();
        }
        if (gesteEvent == GesteEvent.ELLIPSE){
            getPaletteMultimodale().dessinerEllipse();
        }
    }
    //A6
    private void drawFormeCouleur(){
        if (gesteEvent == GesteEvent.RECTANGLE){
            getPaletteMultimodale().dessinerRectangle(couleur);
        }
        if (gesteEvent == GesteEvent.ELLIPSE){
            getPaletteMultimodale().dessinerEllipse(couleur);
        }
    }
    //A7
    private void drawFormePosition(){
        if (gesteEvent == GesteEvent.RECTANGLE){
            getPaletteMultimodale().dessinerRectangle(position);
        }
        if (gesteEvent == GesteEvent.ELLIPSE){
            getPaletteMultimodale().dessinerEllipse(position);
        }
    }
    //A8
    private void drawFormeCouleurPosition(){
        if (gesteEvent == GesteEvent.RECTANGLE){
            getPaletteMultimodale().dessinerRectangle(position, couleur);
        }
        if (gesteEvent == GesteEvent.ELLIPSE){
            getPaletteMultimodale().dessinerEllipse(position, couleur);
        }
    }
    
    //A9
    private void setObjet(){
        nomObjet = getPaletteMultimodale().selectionnerObject();
    }
    //A10
    private void deleteObjet (){
        System.out.println("Suppression en cours");
        getPaletteMultimodale().supprimerObjet(nomObjet);
    }
    
    //A11
    private void deleteObjectColor(){
        getPaletteMultimodale().supprimerObjetCouleur(nomObjet, couleur);
    }
    //A12
    private void deplacer(){
        getPaletteMultimodale().deplacerFormeNom(nomObjet, position);
    }
}
