package fr.etl_off.repository;

import fr.etl_off.model.Additif;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA pour les additifs.
 */
@Repository
public interface AdditifRepository extends JpaRepository<Additif, Long> {

    Optional<Additif> findByNom(String nom);

    @Query("SELECT a.nom, COUNT(p.id) FROM Additif a JOIN a.produits p GROUP BY a.id, a.nom ORDER BY COUNT(p.id) DESC")
    List<Object[]> findTopAdditifs(Pageable pageable);
}
