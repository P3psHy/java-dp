package fr.etl_off.dao.impl;

import fr.etl_off.dao.AdditifDao;
import fr.etl_off.model.Additif;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation JPA du DAO pour les additifs.
 */
@Repository
public class AdditifDaoImpl extends AbstractJpaDao<Additif, Long> implements AdditifDao {

    public AdditifDaoImpl() {
        super(Additif.class);
    }

    @Override
    public Optional<Additif> findByNom(String nom) {
        TypedQuery<Additif> query = entityManager
                .createQuery("SELECT a FROM Additif a WHERE a.nom = :nom", Additif.class)
                .setParameter("nom", nom);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Object[]> findTopAdditifs(int limit) {
        return entityManager.createQuery(
                        "SELECT a.nom, COUNT(p.id) " +
                                "FROM Additif a JOIN a.produits p " +
                                "GROUP BY a.id, a.nom " +
                                "ORDER BY COUNT(p.id) DESC",
                        Object[].class)
                .setMaxResults(limit)
                .getResultList();
    }
}
