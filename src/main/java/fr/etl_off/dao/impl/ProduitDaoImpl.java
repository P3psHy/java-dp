package fr.etl_off.dao.impl;

import fr.etl_off.dao.ProduitDao;
import fr.etl_off.model.Produit;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implémentation JPA du DAO pour les produits.
 */
@Repository
public class ProduitDaoImpl extends AbstractJpaDao<Produit, Long> implements ProduitDao {

    public ProduitDaoImpl() {
        super(Produit.class);
    }

    @Override
    public List<Produit> findTopByMarque(Long marqueId, int limit) {
        TypedQuery<Produit> query = entityManager.createQuery(
                "SELECT p FROM Produit p WHERE p.marque.id = :marqueId ORDER BY p.grade ASC",
                Produit.class);
        query.setParameter("marqueId", marqueId);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Produit> findTopByCategorie(Long categorieId, int limit) {
        TypedQuery<Produit> query = entityManager.createQuery(
                "SELECT p FROM Produit p WHERE p.categorie.id = :categorieId ORDER BY p.grade ASC",
                Produit.class);
        query.setParameter("categorieId", categorieId);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Produit> findTopByMarqueAndCategorie(Long marqueId, Long categorieId, int limit) {
        TypedQuery<Produit> query = entityManager.createQuery(
                "SELECT p FROM Produit p " +
                        "WHERE p.marque.id = :marqueId AND p.categorie.id = :categorieId " +
                        "ORDER BY p.grade ASC",
                Produit.class);
        query.setParameter("marqueId", marqueId);
        query.setParameter("categorieId", categorieId);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
