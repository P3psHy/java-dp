package fr.etl_off.service;

import fr.etl_off.model.Categorie;

/**
 * Service métier pour les catégories.
 */
public interface CategorieService extends GenericService<Categorie, Long> {

    /**
     * Récupère ou crée une catégorie par son nom.
     *
     * @param nom le nom de la catégorie
     * @return la catégorie existante ou nouvellement créée
     */
    Categorie getOrCreate(String nom);
}
