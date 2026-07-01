package fr.etl_off.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Allergene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private double qteMilligrammes;

    private List<Produit> produits;
}
