package fr.etl_off.dao.impl;

import fr.etl_off.dao.MarqueDao;
import fr.etl_off.model.Marque;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Implémentation JPA du DAO pour les marques.
 */
@Repository
public class MarqueDaoImpl extends AbstractJpaDao<Marque, Long> implements MarqueDao {

    public MarqueDaoImpl() {
        super(Marque.class);
    }

    @Override
    public Optional<Marque> findByNom(String nom) {
        TypedQuery<Marque> query = entityManager
                .createQuery("SELECT m FROM Marque m WHERE m.nom = :nom", Marque.class)
                .setParameter("nom", nom);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
