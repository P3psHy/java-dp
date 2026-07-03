package fr.etl_off.repository;

import fr.etl_off.model.Produit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository Spring Data JPA pour les produits.
 */
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query("SELECT p FROM Produit p WHERE p.marque.id = :marqueId ORDER BY p.grade ASC")
    List<Produit> findTopByMarque(@Param("marqueId") Long marqueId, Pageable pageable);

    @Query("SELECT p FROM Produit p WHERE p.categorie.id = :categorieId ORDER BY p.grade ASC")
    List<Produit> findTopByCategorie(@Param("categorieId") Long categorieId, Pageable pageable);

    @Query("SELECT p FROM Produit p WHERE p.marque.id = :marqueId AND p.categorie.id = :categorieId ORDER BY p.grade ASC")
    List<Produit> findTopByMarqueAndCategorie(@Param("marqueId") Long marqueId,
                                                @Param("categorieId") Long categorieId,
                                                Pageable pageable);
}
