package fr.etl_off.model.Comp;

import javax.persistence.*;

import fr.etl_off.model.Ingredient;
import fr.etl_off.model.Produit;

@Entity
public class CompProdIngr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double qteMilligrammes;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}