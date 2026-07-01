package fr.etl_off.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.etl_off.model.Comp.CompProdAddi;

public class Additif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nom;

    private double qteMilligrammes;

    @OneToMany(mappedBy = "additif")
    private List<CompProdAddi> compProdAdditifs;
}
