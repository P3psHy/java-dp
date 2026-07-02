package fr.etl_off.service;

import java.util.List;
import java.util.Optional;

/**
 * Interface générique de service définissant les opérations métier communes.
 *
 * @param <T>  le type de l'entité métier
 * @param <ID> le type de l'identifiant
 */
public interface GenericService<T, ID> {

    /**
     * Crée une nouvelle entité métier.
     *
     * @param entity l'entité à créer
     * @return l'entité créée
     */
    T create(T entity);

    /**
     * Retourne une entité par son identifiant.
     *
     * @param id l'identifiant
     * @return l'entité trouvée
     */
    Optional<T> findById(ID id);

    /**
     * Retourne toutes les entités de ce type.
     *
     * @return la liste des entités
     */
    List<T> findAll();

    /**
     * Met à jour une entité existante.
     *
     * @param entity l'entité à mettre à jour
     * @return l'entité mise à jour
     */
    T update(T entity);

    /**
     * Supprime une entité par son identifiant.
     *
     * @param id l'identifiant de l'entité à supprimer
     */
    void deleteById(ID id);
}
