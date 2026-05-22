package fr.sdv.factory;

import fr.sdv.factory.beans.Additif;
import fr.sdv.factory.beans.Allergene;
import fr.sdv.factory.beans.Element;
import fr.sdv.factory.beans.Ingredient;
import fr.sdv.factory.beans.Enum.TYPE_ELEMENT;
import fr.sdv.factory.beans.Enum.UNITE;


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