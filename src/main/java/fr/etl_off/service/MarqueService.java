package fr.etl_off.service;

import fr.etl_off.model.Marque;

/**
 * Service métier pour les marques.
 */
public interface MarqueService extends GenericService<Marque, Long> {

    /**
     * Récupère ou crée une marque par son nom.
     *
     * @param nom le nom de la marque
     * @return la marque existante ou nouvellement créée
     */
    Marque getOrCreate(String nom);
}
