package org.sebsy.factory.beans;

import org.sebsy.factory.beans.Enum.UNITE;

public class Ingredient extends Element {
    public Ingredient(String nom, double valeur, UNITE unite) {
        super(nom, valeur, unite);
    }
}
