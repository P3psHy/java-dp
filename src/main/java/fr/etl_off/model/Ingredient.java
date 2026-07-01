package fr.etl_off.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nom;

    private double qteMilligrammes;

    private List<Produit> produits;
}
