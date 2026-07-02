package fr.etl_off.dao.impl;

import fr.etl_off.dao.CategorieDao;
import fr.etl_off.model.Categorie;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Implémentation JPA du DAO pour les catégories.
 */
@Repository
public class CategorieDaoImpl extends AbstractJpaDao<Categorie, Long> implements CategorieDao {

    public CategorieDaoImpl() {
        super(Categorie.class);
    }

    @Override
    public Optional<Categorie> findByNom(String nom) {
        TypedQuery<Categorie> query = entityManager
                .createQuery("SELECT c FROM Categorie c WHERE c.nom = :nom", Categorie.class)
                .setParameter("nom", nom);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
