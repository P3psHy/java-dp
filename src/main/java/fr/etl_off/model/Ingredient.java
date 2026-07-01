package fr.etl_off.model;

import java.util.List;

import javax.persistence.*;

import fr.etl_off.model.Comp.CompProdIngr;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nom;

    private double qteMilligrammes;

    @OneToMany(mappedBy = "ingredient")
    private List<CompProdIngr> compProdIngredients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getQteMilligrammes() {
        return qteMilligrammes;
    }

    public void setQteMilligrammes(double qteMilligrammes) {
        this.qteMilligrammes = qteMilligrammes;
    }

    public List<CompProdIngr> getCompProdIngredients() {
        return compProdIngredients;
    }

    public void setCompProdIngredients(List<CompProdIngr> compProdIngredients) {
        this.compProdIngredients = compProdIngredients;
    }
}
