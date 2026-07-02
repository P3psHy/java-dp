package fr.etl_off.service.impl;

import fr.etl_off.dao.GenericDao;
import fr.etl_off.service.GenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation abstraite de {@link GenericService}.
 * Définit les opérations CRUD génériques en s'appuyant sur le DAO correspondant.
 *
 * @param <T>  le type de l'entité métier
 * @param <ID> le type de l'identifiant
 */
public abstract class AbstractGenericService<T, ID> implements GenericService<T, ID> {

    protected final GenericDao<T, ID> dao;

    protected AbstractGenericService(GenericDao<T, ID> dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public T create(T entity) {
        return dao.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public T update(T entity) {
        return dao.update(entity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        dao.deleteById(id);
    }
}
