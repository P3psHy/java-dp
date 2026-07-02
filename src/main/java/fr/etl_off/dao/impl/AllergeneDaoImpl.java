package fr.etl_off.dao.impl;

import fr.etl_off.dao.AllergeneDao;
import fr.etl_off.model.Allergene;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation JPA du DAO pour les allergènes.
 */
@Repository
public class AllergeneDaoImpl extends AbstractJpaDao<Allergene, Long> implements AllergeneDao {

    public AllergeneDaoImpl() {
        super(Allergene.class);
    }

    @Override
    public Optional<Allergene> findByNom(String nom) {
        TypedQuery<Allergene> query = entityManager
                .createQuery("SELECT a FROM Allergene a WHERE a.nom = :nom", Allergene.class)
                .setParameter("nom", nom);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Object[]> findTopAllergenes(int limit) {
        return entityManager.createQuery(
                        "SELECT a.nom, COUNT(p.id) " +
                                "FROM Allergene a JOIN a.produits p " +
                                "GROUP BY a.id, a.nom " +
                                "ORDER BY COUNT(p.id) DESC",
                        Object[].class)
                .setMaxResults(limit)
                .getResultList();
    }
}
