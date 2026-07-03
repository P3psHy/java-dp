package fr.etl_off.repository;

import fr.etl_off.model.Ingredient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA pour les ingrédients.
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByNom(String nom);

    @Query("SELECT i.nom, COUNT(p.id) FROM Ingredient i JOIN i.produits p GROUP BY i.id, i.nom ORDER BY COUNT(p.id) DESC")
    List<Object[]> findTopIngredients(Pageable pageable);
}
