package fr.etl_off.cache;

import fr.etl_off.model.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache mémoire pour les produits.
 *
 * Objectif :
 * - éviter les requêtes SQL répétées
 * - accélérer les recherches fréquentes (brand, category, id)
 *
 * Utilisé principalement par la couche Service.
 */
public class ProduitCache {

    /**
     * Cache principal par ID produit
     */
    private final Map<Long, Produit> cacheById = new ConcurrentHashMap<>();

    /**
     * Cache des produits par marque
     */
    private final Map<String, List<Produit>> cacheByBrand = new ConcurrentHashMap<>();

    /**
     * Cache des produits par catégorie
     */
    private final Map<String, List<Produit>> cacheByCategory = new ConcurrentHashMap<>();

    /**
     * Cache combiné marque + catégorie
     */
    private final Map<String, List<Produit>> cacheByBrandAndCategory = new ConcurrentHashMap<>();

    // =========================
    // CACHE BY ID
    // =========================

    public Produit getById(Long id) {
        return cacheById.get(id);
    }

    public void put(Produit produit) {
        if (produit == null || produit.getId() == null) {
            return;
        }
        cacheById.put(produit.getId(), produit);
    }

    // =========================
    // CACHE BRAND
    // =========================

    public List<Produit> getByBrand(String brand) {
        return cacheByBrand.get(brand);
    }

    public void putByBrand(String brand, List<Produit> produits) {
        cacheByBrand.put(brand, new ArrayList<>(produits));
    }

    // =========================
    // CACHE CATEGORY
    // =========================

    public List<Produit> getByCategory(String category) {
        return cacheByCategory.get(category);
    }

    public void putByCategory(String category, List<Produit> produits) {
        cacheByCategory.put(category, new ArrayList<>(produits));
    }

    // =========================
    // CACHE BRAND + CATEGORY
    // =========================

    private String buildKey(String brand, String category) {
        return brand + "::" + category;
    }

    public List<Produit> getByBrandAndCategory(String brand, String category) {
        return cacheByBrandAndCategory.get(buildKey(brand, category));
    }

    public void putByBrandAndCategory(String brand, String category, List<Produit> produits) {
        cacheByBrandAndCategory.put(buildKey(brand, category), new ArrayList<>(produits));
    }

    // =========================
    // INVALIDATION
    // =========================

    /**
     * À appeler après INSERT / UPDATE / DELETE
     */
    public void clear() {
        cacheById.clear();
        cacheByBrand.clear();
        cacheByCategory.clear();
        cacheByBrandAndCategory.clear();
    }
}