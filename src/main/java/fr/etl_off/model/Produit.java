package fr.etl_off.model;

import jakarta.persistence.*;

import fr.etl_off.model.Additif;
import fr.etl_off.model.Allergene;
import fr.etl_off.model.Ingredient;

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
    @JoinTable(name = "produit_ingredient",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;

    @ManyToMany
    @JoinTable(name = "produit_allergene",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "allergene_id"))
    private List<Allergene> allergenes;

    @ManyToMany
    @JoinTable(name = "produit_additif",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "additif_id"))
    private List<Additif> additifs;

    @Column(nullable = true)
    private Float energie100g;
    @Column(nullable = true)
    private Float graisse100g;
    @Column(nullable = true)
    private Float sucres100g;
    @Column(nullable = true)
    private Float fibres100g;
    @Column(nullable = true)
    private Float proteines100g;
    @Column(nullable = true)
    private Float sel100g;
    @Column(nullable = true)
    private Float vitA100g;
    @Column(nullable = true)
    private Float vitD100g;
    @Column(nullable = true)
    private Float vitE100g;
    @Column(nullable = true)
    private Float vitK100g;
    @Column(nullable = true)
    private Float vitC100g;
    @Column(nullable = true)
    private Float vitB1100g;
    @Column(nullable = true)
    private Float vitB2100g;
    @Column(nullable = true)
    private Float vitPP100g;
    @Column(nullable = true)
    private Float vitB6100g;
    @Column(nullable = true)
    private Float vitB9100g;
    @Column(nullable = true)
    private Float vitB12100g;
    @Column(nullable = true)
    private Float calcium100g;
    @Column(nullable = true)
    private Float magnesium100g;
    @Column(nullable = true)
    private Float iron100g;
    @Column(nullable = true)
    private Float fer100g;
    @Column(nullable = true)
    private Float betaCarotene100g;
    @Column(nullable = true)
    private Float presenceHuilePalme;

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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Allergene> getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(List<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    public List<Additif> getAdditifs() {
        return additifs;
    }

    public void setAdditifs(List<Additif> additifs) {
        this.additifs = additifs;
    }

    public void addIngredient(Ingredient ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new java.util.ArrayList<>();
        }
        this.ingredients.add(ingredient);
    }

    public void addAllergene(Allergene allergene) {
        if (this.allergenes == null) {
            this.allergenes = new java.util.ArrayList<>();
        }
        this.allergenes.add(allergene);
    }

    public void addAdditif(Additif additif) {
        if (this.additifs == null) {
            this.additifs = new java.util.ArrayList<>();
        }
        this.additifs.add(additif);
    }

    public Float getEnergie100g() {
        return energie100g;
    }

    public void setEnergie100g(Float energie100g) {
        this.energie100g = energie100g;
    }

    public Float getGraisse100g() {
        return graisse100g;
    }

    public void setGraisse100g(Float graisse100g) {
        this.graisse100g = graisse100g;
    }

    public Float getSucres100g() {
        return sucres100g;
    }

    public void setSucres100g(Float sucres100g) {
        this.sucres100g = sucres100g;
    }

    public Float getFibres100g() {
        return fibres100g;
    }

    public void setFibres100g(Float fibres100g) {
        this.fibres100g = fibres100g;
    }

    public Float getProteines100g() {
        return proteines100g;
    }

    public void setProteines100g(Float proteines100g) {
        this.proteines100g = proteines100g;
    }

    public Float getSel100g() {
        return sel100g;
    }

    public void setSel100g(Float sel100g) {
        this.sel100g = sel100g;
    }

    public Float getVitA100g() {
        return vitA100g;
    }

    public void setVitA100g(Float vitA100g) {
        this.vitA100g = vitA100g;
    }

    public Float getVitD100g() {
        return vitD100g;
    }

    public void setVitD100g(Float vitD100g) {
        this.vitD100g = vitD100g;
    }

    public Float getVitE100g() {
        return vitE100g;
    }

    public void setVitE100g(Float vitE100g) {
        this.vitE100g = vitE100g;
    }

    public Float getVitK100g() {
        return vitK100g;
    }

    public void setVitK100g(Float vitK100g) {
        this.vitK100g = vitK100g;
    }

    public Float getVitC100g() {
        return vitC100g;
    }

    public void setVitC100g(Float vitC100g) {
        this.vitC100g = vitC100g;
    }

    public Float getVitB1100g() {
        return vitB1100g;
    }

    public void setVitB1100g(Float vitB1100g) {
        this.vitB1100g = vitB1100g;
    }

    public Float getVitB2100g() {
        return vitB2100g;
    }

    public void setVitB2100g(Float vitB2100g) {
        this.vitB2100g = vitB2100g;
    }

    public Float getVitPP100g() {
        return vitPP100g;
    }

    public void setVitPP100g(Float vitPP100g) {
        this.vitPP100g = vitPP100g;
    }

    public Float getVitB6100g() {
        return vitB6100g;
    }

    public void setVitB6100g(Float vitB6100g) {
        this.vitB6100g = vitB6100g;
    }

    public Float getVitB9100g() {
        return vitB9100g;
    }

    public void setVitB9100g(Float vitB9100g) {
        this.vitB9100g = vitB9100g;
    }

    public Float getVitB12100g() {
        return vitB12100g;
    }

    public void setVitB12100g(Float vitB12100g) {
        this.vitB12100g = vitB12100g;
    }

    public Float getCalcium100g() {
        return calcium100g;
    }

    public void setCalcium100g(Float calcium100g) {
        this.calcium100g = calcium100g;
    }

    public Float getMagnesium100g() {
        return magnesium100g;
    }

    public void setMagnesium100g(Float magnesium100g) {
        this.magnesium100g = magnesium100g;
    }

    public Float getIron100g() {
        return iron100g;
    }

    public void setIron100g(Float iron100g) {
        this.iron100g = iron100g;
    }

    public Float getFer100g() {
        return fer100g;
    }

    public void setFer100g(Float fer100g) {
        this.fer100g = fer100g;
    }

    public Float getBetaCarotene100g() {
        return betaCarotene100g;
    }

    public void setBetaCarotene100g(Float betaCarotene100g) {
        this.betaCarotene100g = betaCarotene100g;
    }

    public Float getPresenceHuilePalme() {
        return presenceHuilePalme;
    }

    public void setPresenceHuilePalme(Float presenceHuilePalme) {
        this.presenceHuilePalme = presenceHuilePalme;
    }
}