package fr.sdv.composite;

public class Employe implements IElement{
    
    private String nom;
    private String prenom;
    private double salaire;


    public double calculerSalaire() {

        double salaireTotal = 0;
        salaireTotal += getSalaire();

        return salaireTotal;
    };


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

}
