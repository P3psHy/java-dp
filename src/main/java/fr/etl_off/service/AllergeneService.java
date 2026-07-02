package fr.etl_off.service;

import fr.etl_off.model.Allergene;

import java.util.List;

/**
 * Service métier pour les allergènes.
 */
public interface AllergeneService extends GenericService<Allergene, Long> {

    /**
     * Récupère ou crée un allergène par son nom nettoyé.
     *
     * @param nom le nom de l'allergène
     * @return l'allergène existant ou nouvellement créé
     */
    Allergene getOrCreate(String nom);

    /**
     * Retourne les allergènes les plus courants.
     *
     * @param limit le nombre maximum de résultats
     * @return les allergènes les plus courants avec leur nombre d'occurrences
     */
    List<Object[]> findTop(int limit);
}
