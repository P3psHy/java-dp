package fr.etl_off.model;

import javax.persistence.*;

import fr.etl_off.model.Comp.CompProdAddi;
import fr.etl_off.model.Comp.CompProdAller;
import fr.etl_off.model.Comp.CompProdIngr;

import java.util.List;

@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String grade;

    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<CompProdAddi> compProdAdditifs;

    @OneToMany(mappedBy = "produit")
    private List<CompProdAller> compProdAllergenes;

    @OneToMany(mappedBy = "produit")
    private List<CompProdIngr> compProdIngredients;

    // getters & setters

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<CompProdAddi> getCompProdAdditifs() {
        return compProdAdditifs;
    }

    public void setCompProdAdditifs(List<CompProdAddi> compProdAdditifs) {
        this.compProdAdditifs = compProdAdditifs;
    }

    public List<CompProdAller> getCompProdAllergenes() {
        return compProdAllergenes;
    }

    public void setCompProdAllergenes(List<CompProdAller> compProdAllergenes) {
        this.compProdAllergenes = compProdAllergenes;
    }

    public List<CompProdIngr> getCompProdIngredients() {
        return compProdIngredients;
    }

    public void setCompProdIngredients(List<CompProdIngr> compProdIngredients) {
        this.compProdIngredients = compProdIngredients;
    }
}