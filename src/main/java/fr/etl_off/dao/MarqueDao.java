package fr.etl_off.dao;

import fr.etl_off.model.Marque;

import java.util.Optional;

/**
 * DAO dédié à l'entité {@link Marque}.
 */
public interface MarqueDao extends GenericDao<Marque, Long> {

    /**
     * Recherche une marque par son nom exact.
     *
     * @param nom le nom de la marque
     * @return la marque correspondante si elle existe
     */
    Optional<Marque> findByNom(String nom);
}
