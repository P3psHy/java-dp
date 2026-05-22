package fr.sdv.factory.beans;

import fr.sdv.factory.beans.Enum.UNITE;

public abstract class Element {
    
    private String nom;
    private double valeur;
    private UNITE unite;

    public Element(String nom, double valeur, UNITE unite) {

        this.nom = nom;
        this.valeur = valeur;
        this.unite = unite;
    }


    public String getNom() {
        return nom;
    }

    public double getValeur() {
        return valeur;
    }

    public UNITE getUnite() {
        return unite;
    }



}
