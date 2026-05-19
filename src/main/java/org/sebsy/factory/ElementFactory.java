package org.sebsy.factory;

import org.sebsy.factory.beans.Element;
import org.sebsy.factory.beans.Ingredient;
import org.sebsy.factory.beans.Additif;
import org.sebsy.factory.beans.Allergene;

import org.sebsy.factory.beans.Enum.UNITE;
import org.sebsy.factory.beans.Enum.TYPE_ELEMENT;


public class ElementFactory {

    public Element createElement(TYPE_ELEMENT type, String nom, double valeur, UNITE unite) {

        if (type == null) {
            throw new IllegalArgumentException("Type non renseigné");
        }

        switch (type) {

            case INGREDIENT:
                return new Ingredient(nom, valeur, unite);

            case ADDITIF:
                return new Additif(nom, valeur, unite);

            case ALLERGENE:
                return new Allergene(nom, valeur, unite);

            default:
                throw new IllegalArgumentException("Type non renseigné : " + type);
        }
    }
}