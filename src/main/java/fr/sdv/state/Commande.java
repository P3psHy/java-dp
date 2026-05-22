package fr.sdv.state;

import fr.sdv.state.Enum.Etat;
import java.util.List;

public class Commande {
    
    private float montant;
    private Etat etat;
    private String adresseLivraison;
    private List<Produit> produits;


    public void ajouterProduit(Produit newProduit){

        if (this.etat == Etat.CREATION) {
            this.produits.add(newProduit);
        }
    }

    public void payer() {
        if (this.etat == Etat.CREATION) {

            montant = this.produits.size() * 0.5f;
            setMontant(montant);

            setEtat(Etat.PAIEMENT);
        }
    }

    public void livrer(String adresseLivraison) {
        if (this.etat == Etat.PAIEMENT) {

            setAdresseLivraison(adresseLivraison);
            setEtat(Etat.EN_LIVRAISON);
        }
    }

    public String annuler() {
        switch (this.etat) {

            case CREATION:
            case PAIEMENT:
                this.setEtat(Etat.ANNULEE);
                return "La commande a été annulée avec succès.";

            case EN_LIVRAISON:
                return "La commande est déjà en livraison, impossible de l'annuler.";

            case ANNULEE:
                return "La commande est déjà annulée.";

            default:
                return "État inconnu.";
        }
    }



    // Getters and Setters

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
    


}
