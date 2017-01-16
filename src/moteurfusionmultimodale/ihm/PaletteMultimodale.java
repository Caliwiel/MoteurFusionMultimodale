/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moteurfusionmultimodale.ihm;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import fr.irit.elipse.enseignement.isia.PaletteGraphique;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import moteurfusionmultimodale.AgentReconnaisseurVocal;
import moteurfusionmultimodale.Reconaisseur;
import moteurfusionmultimodale.StateMachine;
import moteurfusionmultimodale.Stroke;

/**
 *
 * @author bouzekel
 */
public class PaletteMultimodale {

    private Ivy bus;
    private PaletteGraphique palette;

    private Point lastDragPosition = new Point();
    private Point positionSelect = new Point();

    private String nameCurrentObject = "";

    private String nameSelectedObject = "";
    
    private String couleur = "";

    private Stroke current_stroke;
    
    private StateMachine machine;

    public StateMachine getMachine() {
        return machine;
    }

    public void setMachine(StateMachine machine) {
        this.machine = machine;
    }

    public PaletteMultimodale(String adresse) throws IvyException {
        palette = new PaletteGraphique(adresse, 10, 10, 500, 550);

        palette.setVisible(true);

        try {
            bus = new Ivy("Recepteur", "je reçoit", null);
            bus.start("127.255.255.255:2010");

            bus.bindMsg("Palette:MouseMoved x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {

                    lastDragPosition.x = Integer.parseInt(strings[0]);
                    lastDragPosition.y = Integer.parseInt(strings[1]);
                    try {
                        bus.sendMsg("Palette:TesterPoint x=" + lastDragPosition.x + " y=" + lastDragPosition.y);
                    } catch (IvyException ex) {
                        Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

            bus.bindMsg("Palette:ResultatTesterPoint x=(.*) y=(.*) nom=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    nameCurrentObject = strings[2];
                }
            });

            bus.bindMsg("Palette:MousePressed x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    current_stroke = new Stroke();
                    current_stroke.addPoint(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]));
                    
                    machine.handleClickEvent();
                }
            });

            bus.bindMsg("Palette:MouseReleased x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    current_stroke.addPoint(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]));
                    String message = "Geste coord=";
                    //System.out.println(message);
                    List<Point2D.Double> points = current_stroke.getPoints();
                    for (Point2D.Double point : points) {
                        message += point.getX() + "," + point.getY() + ";";
                    }
                    try {
                        bus.sendMsg(message);
                        //System.out.println(message);
                    } catch (IvyException ex) {
                        Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            bus.bindMsg("Palette:MouseDragged x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    current_stroke.addPoint(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]));
                }
            });

        } catch (IvyException ex) {
            Logger.getLogger(AgentReconnaisseurVocal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Point getLastPosition() {
        return lastDragPosition;
    }
    
    public void setCouleur (String c){
        this.couleur = c;
    }

    public String selectionnerObject() {
        //nameSelectedObject = nameCurrentObject;
        return nameCurrentObject;
    }
    
    public void setPositionObject() {
        positionSelect = getLastPosition();
    }

    /**
     * *****************************************************
     */
    /**
     * Dessin d'un rectangle sans position
     */
    public void dessinerRectangle() {
        try {
            bus.sendMsg("Palette:CreerRectangle");
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Dessin d'un rectangle avec position
     */
    public void dessinerRectangle(Point p) {
        try {
            bus.sendMsg("Palette:CreerRectangle x=" + p.x + " y=" + p.y);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Dessin d'une ellipse avec une couleur
     */
    public void dessinerRectangle(String color) {
        try {
            bus.sendMsg("Palette:CreerRectangle couleurFond="+color);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Dessin d'un rectangle avec position et couleur
     */
    public void dessinerRectangle(Point p, String color) {
        try {
            bus.sendMsg("Palette:CreerRectangle x=" + p.x + " y=" + p.y + " couleurFond="+color);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *****************************************************
     */
    /**
     * Dessin d'une ellipse sans position
     */
    public void dessinerEllipse() {
        try {
            bus.sendMsg("Palette:CreerEllipse");
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Dessin d'une ellipse avec position
     */
    public void dessinerEllipse(Point p) {
        try {
            bus.sendMsg("Palette:CreerEllipse x=" + p.x + " y=" + p.y);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Dessin d'une ellipse avec une couleur
     */
    public void dessinerEllipse(String color) {
        try {
            bus.sendMsg("Palette:CreerEllipse couleurFond="+color);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Dessin d'une ellipse avec position et couleur
     */
    public void dessinerEllipse(Point p, String color) {
        try {
            bus.sendMsg("Palette:CreerEllipse x=" + p.x + " y=" + p.y + " couleurFond="+color);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *****************************************************
     */
    /**
     * Changer de couleur une forme à partir d'un point p
     */
    public void changerCouleurFondPoint(Point p, String couleur) {
        changerCouleurFondNom(nameCurrentObject, couleur);

    }

    /**
     * Changer de couleur une forme à partir d'un nom
     */
    public void changerCouleurFondNom(String nom, String couleur) {
        try {
            String color = "";
            switch (couleur) {
                case "rouge":
                    color = "red";
                    break;
                case "bleu":
                    color = "blue";
                    break;
                case "vert":
                    color = "green";
                    break;
                case "jaune":
                    color = "yellow";
                    break;
                case "noir":
                    color = "black";
                    break;
                case "blanc":
                    color = "white";
                    break;
            }
            bus.sendMsg("Palette:ModifierCouleur nom=" + nom + " couleurFond=" + color);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *****************************************************
     */
    /**
     * Déplacer une forme à partir d'un point
     */
    public void deplacerFormePoint(Point pDep, Point pArr) {
        deplacerFormeNom(nameCurrentObject, new Point(10, 10));
    }

    /**
     * Déplacer une forme à partir d'un nom
     */
    public void deplacerFormeNom(String nom, Point pArr) {
        try {
            bus.sendMsg("Palette:DeplacerObjet nom=" + nom + " x=" + pArr.x + " y=" + pArr.y);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *****************************************************
     */
    /**
     * Déplacer une forme à partir d'un point
     */
    public void modifierTailleObjetPoint(Point pDep, int l, int h) {
        modifierTailleObjetNom(nameCurrentObject, l, h);
    }

    /**
     * Déplacer une forme à partir d'un nom
     */
    public void modifierTailleObjetNom(String nom, int l, int h) {
        try {
            bus.sendMsg("Palette:ModifierTailleObjet nom=" + nom + " longueur=" + l + " hauteur=" + h);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /******** TODOOOOOO *****/
    public void supprimerObjet(String nom){
        try {
            bus.sendMsg("Palette:SupprimerObjet nom=" + nom);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void supprimerObjetCouleur(String nom, String couleur){
        try {
            bus.sendMsg("Palette:SupprimerObjet nom=" + nom + " couleurFond="+couleur);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteMultimodale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
