package fr.sdv.state;

import fr.sdv.state.Enum.Etat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;


public class CommandeStateTest {

    private Commande commande;

    @Before
    public void setUp() {
        commande = new Commande();
        commande.setEtat(Etat.CREATION);
        commande.setMontant(0f);
        commande.setAdresseLivraison(null);

        List<Produit> produits = new java.util.ArrayList<>();
        commande.setProduits(produits);
    }

    @Test
    public void testAjouterProduitEtatCreation() {

        Produit p = new Produit();

        commande.ajouterProduit(p);

        assertEquals(1, commande.getProduits().size());
    }

    @Test
    public void testAjouterProduitEtatPaiementInterdit() {

        commande.setEtat(Etat.PAIEMENT);

        Produit p = new Produit();

        commande.ajouterProduit(p);

        assertEquals(0, commande.getProduits().size());
    }

    @Test
    public void testPaiement() {

        commande.ajouterProduit(new Produit());
        commande.ajouterProduit(new Produit());

        commande.payer();

        assertEquals(Etat.PAIEMENT, commande.getEtat());
        assertEquals(1.0f, commande.getMontant(), 0.001);
    }

    @Test
    public void testLivraison() {

        commande.setEtat(Etat.PAIEMENT);

        commande.livrer("Paris");

        assertEquals(Etat.EN_LIVRAISON, commande.getEtat());
        assertEquals("Paris", commande.getAdresseLivraison());
    }

    @Test
    public void testAnnulationCreation() {

        String result = commande.annuler();

        assertEquals(Etat.ANNULEE, commande.getEtat());
        assertEquals("La commande a été annulée avec succès.", result);
    }

    @Test
    public void testAnnulationEnLivraison() {

        commande.setEtat(Etat.EN_LIVRAISON);

        String result = commande.annuler();

        assertEquals(Etat.EN_LIVRAISON, commande.getEtat());
        assertEquals("La commande est déjà en livraison, impossible de l'annuler.", result);
    }

    @Test
    public void testAnnulationDejaAnnulee() {

        commande.setEtat(Etat.ANNULEE);

        String result = commande.annuler();

        assertEquals(Etat.ANNULEE, commande.getEtat());
        assertEquals("La commande est déjà annulée.", result);
    }
}