package fr.etl_off.service.impl;

import fr.etl_off.service.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation abstraite de {@link GenericService}.
 * Définit les opérations CRUD génériques en s'appuyant sur un repository Spring Data JPA.
 *
 * @param <T>  le type de l'entité métier
 * @param <ID> le type de l'identifiant
 */
public abstract class AbstractGenericService<T, ID> implements GenericService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected AbstractGenericService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
