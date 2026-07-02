package fr.etl_off.dao.impl;

import fr.etl_off.dao.IngredientDao;
import fr.etl_off.model.Ingredient;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation JPA du DAO pour les ingrédients.
 */
@Repository
public class IngredientDaoImpl extends AbstractJpaDao<Ingredient, Long> implements IngredientDao {

    public IngredientDaoImpl() {
        super(Ingredient.class);
    }

    @Override
    public Optional<Ingredient> findByNom(String nom) {
        TypedQuery<Ingredient> query = entityManager
                .createQuery("SELECT i FROM Ingredient i WHERE i.nom = :nom", Ingredient.class)
                .setParameter("nom", nom);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Object[]> findTopIngredients(int limit) {
        return entityManager.createQuery(
                        "SELECT i.nom, COUNT(p.id) " +
                                "FROM Ingredient i JOIN i.produits p " +
                                "GROUP BY i.id, i.nom " +
                                "ORDER BY COUNT(p.id) DESC",
                        Object[].class)
                .setMaxResults(limit)
                .getResultList();
    }
}
