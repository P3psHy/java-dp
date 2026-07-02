package fr.etl_off.service;

import fr.etl_off.model.Additif;

import java.util.List;

/**
 * Service métier pour les additifs.
 */
public interface AdditifService extends GenericService<Additif, Long> {

    /**
     * Récupère ou crée un additif par son nom nettoyé.
     *
     * @param nom le nom de l'additif
     * @return l'additif existant ou nouvellement créé
     */
    Additif getOrCreate(String nom);

    /**
     * Retourne les additifs les plus courants.
     *
     * @param limit le nombre maximum de résultats
     * @return les additifs les plus courants avec leur nombre d'occurrences
     */
    List<Object[]> findTop(int limit);
}
