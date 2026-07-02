package fr.etl_off.dao.impl;

import fr.etl_off.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation abstraite du pattern DAO avec JPA.
 * Fournit les opérations CRUD génériques communes à toutes les entités.
 *
 * @param <T>  le type de l'entité
 * @param <ID> le type de l'identifiant
 */
public abstract class AbstractJpaDao<T, ID> implements GenericDao<T, ID> {

    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Constructeur protégé réservé aux sous-classes.
     *
     * @param clazz la classe de l'entité gérée
     */
    protected AbstractJpaDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public List<T> findAll() {
        String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e";
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        return query.getResultList();
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }
}
