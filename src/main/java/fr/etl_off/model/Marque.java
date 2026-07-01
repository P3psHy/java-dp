package fr.etl_off.model;

import java.util.List;
import javax.persistence.*;

@Entity
public class Marque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nom;

    @OneToMany(mappedBy = "marque")
    private List<Produit> produits;
}