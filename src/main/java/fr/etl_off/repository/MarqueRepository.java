package fr.etl_off.repository;

import fr.etl_off.model.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Spring Data JPA pour les marques.
 */
@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {

    Optional<Marque> findByNom(String nom);
}
