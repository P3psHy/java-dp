package fr.etl_off.model;

import java.util.List;

import javax.persistence.*;

public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nom;

    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits;
}
