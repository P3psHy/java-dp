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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getQteMilligrammes() {
        return qteMilligrammes;
    }

    public void setQteMilligrammes(double qteMilligrammes) {
        this.qteMilligrammes = qteMilligrammes;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}