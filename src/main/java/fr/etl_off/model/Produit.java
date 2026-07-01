package fr.etl_off.model;

import javax.persistence.*;

import fr.etl_off.model.Comp.CompProdAddi;

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

    @ManyToMany
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "produit")
    private List<CompProdAddi> compProdAdditifs;

    @ManyToMany
    private List<Allergene> allergenes;

    // getters & setters


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
}