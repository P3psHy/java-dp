package fr.etl_off.dao;

import fr.etl_off.model.Additif;

import java.util.List;
import java.util.Optional;

/**
 * DAO dédié à l'entité {@link Additif}.
 */
public interface AdditifDao extends GenericDao<Additif, Long> {

    /**
     * Recherche un additif par son nom exact.
     *
     * @param nom le nom de l'additif
     * @return l'additif correspondant si il existe
     */
    Optional<Additif> findByNom(String nom);

    /**
     * Retourne les additifs les plus courants dans les produits, avec leur nombre d'occurrences.
     *
     * @param limit le nombre maximum de résultats
     * @return une liste de tableaux {additif, occurrences}
     */
    List<Object[]> findTopAdditifs(int limit);
}
