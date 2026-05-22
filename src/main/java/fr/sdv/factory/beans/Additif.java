package fr.sdv.factory.beans;

import fr.sdv.factory.beans.Enum.UNITE;

public class Additif extends Element {
    public Additif(String nom, double valeur, UNITE unite) {
        super(nom, valeur, unite);
    }
}
