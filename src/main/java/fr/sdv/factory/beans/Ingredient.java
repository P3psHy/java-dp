package fr.sdv.factory.beans;

import fr.sdv.factory.beans.Enum.UNITE;

public class Ingredient extends Element {
    public Ingredient(String nom, double valeur, UNITE unite) {
        super(nom, valeur, unite);
    }
}
