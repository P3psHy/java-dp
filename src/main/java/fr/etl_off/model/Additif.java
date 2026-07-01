package fr.etl_off.model;

import java.util.List;

import javax.persistence.*;

import fr.etl_off.model.Comp.CompProdAddi;

@Entity
public class Additif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nom;

    private double qteMilligrammes;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId()
    {
		return this.id;
	}


    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getQteMilligrammes() {
        return this.qteMilligrammes;
    }

    public void setQteMilligrammes(double qteMilligrammes) {
        this.qteMilligrammes = qteMilligrammes;
    }

    @OneToMany(mappedBy = "additif")
    private List<CompProdAddi> compProdAdditifs;
}


