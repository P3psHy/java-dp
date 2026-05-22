package fr.sdv.strategy;

import fr.sdv.strategy.Enum.TypeTri;

public class StrategyFactory {
 
    public IStrategy createStrategy(TypeTri type) {

        if (type == null) {
            throw new IllegalArgumentException("Type non renseigné");
        }

        switch (type) {

            case BUBBLE:
                return new Bubble();

            case INSERTION:
                return new Insertion();

            case SELECTION:
                return new Selection();

            default:
                throw new IllegalArgumentException("Type non renseigné : " + type);
        }
    }


}
