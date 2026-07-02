package fr.etl_off.dao;

import fr.etl_off.model.Categorie;

import java.util.Optional;

/**
 * DAO dédié à l'entité {@link Categorie}.
 */
public interface CategorieDao extends GenericDao<Categorie, Long> {

    /**
     * Recherche une catégorie par son nom exact.
     *
     * @param nom le nom de la catégorie
     * @return la catégorie correspondante si elle existe
     */
    Optional<Categorie> findByNom(String nom);
}
