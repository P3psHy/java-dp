package fr.etl_off.service;

import fr.etl_off.model.Ingredient;

import java.util.List;

/**
 * Service métier pour les ingrédients.
 */
public interface IngredientService extends GenericService<Ingredient, Long> {

    /**
     * Récupère ou crée un ingrédient par son nom nettoyé.
     *
     * @param nom le nom de l'ingrédient
     * @return l'ingrédient existant ou nouvellement créé
     */
    Ingredient getOrCreate(String nom);

    /**
     * Retourne les ingrédients les plus courants.
     *
     * @param limit le nombre maximum de résultats
     * @return les ingrédients les plus courants avec leur nombre d'occurrences
     */
    List<Object[]> findTop(int limit);
}
