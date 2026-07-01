package fr.etl_off.model.Comp;

import javax.persistence.*;

import fr.etl_off.model.Allergene;
import fr.etl_off.model.Produit;

@Entity
public class CompProdAller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double qteMilligrammes;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "allergene_id")
    private Allergene allergene;
}