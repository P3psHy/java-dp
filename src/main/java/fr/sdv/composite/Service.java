package fr.sdv.composite;

import java.util.List;

public class Service implements IElement {
    
    private String nom;
    private List<IElement> elements = new java.util.ArrayList<>()   ;


    public double calculerSalaire() {

        double salaireTotal = 0;

        for (IElement element : elements) {
            salaireTotal += element.calculerSalaire();
        }

        return salaireTotal;
    };

    // getters et setters

     public String getNom() {
         return nom;
     }

     public void setNom(String nom) {
         this.nom = nom;
     }

     public void addElement(IElement element) {
         elements.add(element);
     }

}
