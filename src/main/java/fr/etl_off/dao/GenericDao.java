package fr.etl_off.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface générique définissant les opérations CRUD communes
 * à toutes les entités métier.
 *
 * @param <T>  le type de l'entité
 * @param <ID> le type de l'identifiant de l'entité
 */
public interface GenericDao<T, ID> {

    /**
     * Persiste une nouvelle entité en base de données.
     *
     * @param entity l'entité à persister
     * @return l'entité persistée (avec son identifiant généré)
     */
    T save(T entity);

    /**
     * Recherche une entité par son identifiant.
     *
     * @param id l'identifiant recherché
     * @return l'entité encapsulée dans un {@link Optional} si elle existe
     */
    Optional<T> findById(ID id);

    /**
     * Retourne la liste de toutes les entités de ce type.
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
     * Supprime une entité de la base de données.
     *
     * @param entity l'entité à supprimer
     */
    void delete(T entity);

    /**
     * Supprime une entité par son identifiant.
     *
     * @param id l'identifiant de l'entité à supprimer
     */
    void deleteById(ID id);
}
