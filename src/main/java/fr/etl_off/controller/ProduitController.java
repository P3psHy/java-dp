package fr.etl_off.controller;

import fr.etl_off.model.Produit;
import fr.etl_off.service.ProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST exposant les endpoints liés aux produits.
 */
@RestController
@RequestMapping("/products")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    /**
     * Retourne le top N produits d'une marque, triés par grade nutritionnel.
     *
     * @param brand le nom de la marque
     * @param limit le nombre de résultats souhaités
     * @return la liste des produits
     */
    @GetMapping("/top-by-brand")
    public ResponseEntity<List<Produit>> topByBrand(
            @RequestParam String brand,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(produitService.findTopByBrand(brand, limit));
    }

    /**
     * Retourne le top N produits d'une catégorie, triés par grade nutritionnel.
     *
     * @param category le nom de la catégorie
     * @param limit    le nombre de résultats souhaités
     * @return la liste des produits
     */
    @GetMapping("/top-by-category")
    public ResponseEntity<List<Produit>> topByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(produitService.findTopByCategory(category, limit));
    }

    /**
     * Retourne le top N produits d'une marque et d'une catégorie, triés par grade nutritionnel.
     *
     * @param brand    le nom de la marque
     * @param category le nom de la catégorie
     * @param limit    le nombre de résultats souhaités
     * @return la liste des produits
     */
    @GetMapping("/top-by-brand-category")
    public ResponseEntity<List<Produit>> topByBrandAndCategory(
            @RequestParam String brand,
            @RequestParam String category,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(produitService.findTopByBrandAndCategory(brand, category, limit));
    }
}
