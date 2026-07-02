package fr.etl_off.dao;

import fr.etl_off.model.Ingredient;

import java.util.List;
import java.util.Optional;

/**
 * DAO dédié à l'entité {@link Ingredient}.
 */
public interface IngredientDao extends GenericDao<Ingredient, Long> {

    /**
     * Recherche un ingrédient par son nom exact.
     *
     * @param nom le nom de l'ingrédient
     * @return l'ingrédient correspondant si il existe
     */
    Optional<Ingredient> findByNom(String nom);

    /**
     * Retourne les ingrédients les plus courants dans les produits, avec leur nombre d'occurrences.
     *
     * @param limit le nombre maximum de résultats
     * @return une liste de tableaux {ingredient, occurrences}
     */
    List<Object[]> findTopIngredients(int limit);
}
