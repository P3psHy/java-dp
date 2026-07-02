package fr.etl_off.dao;

import fr.etl_off.model.Allergene;

import java.util.List;
import java.util.Optional;

/**
 * DAO dédié à l'entité {@link Allergene}.
 */
public interface AllergeneDao extends GenericDao<Allergene, Long> {

    /**
     * Recherche un allergène par son nom exact.
     *
     * @param nom le nom de l'allergène
     * @return l'allergène correspondant si il existe
     */
    Optional<Allergene> findByNom(String nom);

    /**
     * Retourne les allergènes les plus courants dans les produits, avec leur nombre d'occurrences.
     *
     * @param limit le nombre maximum de résultats
     * @return une liste de tableaux {allergene, occurrences}
     */
    List<Object[]> findTopAllergenes(int limit);
}
