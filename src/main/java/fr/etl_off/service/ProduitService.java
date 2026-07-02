package fr.etl_off.service;

import fr.etl_off.model.Produit;

import java.util.List;

/**
 * Service métier pour les produits.
 */
public interface ProduitService extends GenericService<Produit, Long> {

    /**
     * Retourne les meilleurs produits d'une marque, triés par grade nutritionnel.
     *
     * @param brandName le nom de la marque
     * @param limit     le nombre maximum de résultats
     * @return les produits trouvés
     */
    List<Produit> findTopByBrand(String brandName, int limit);

    /**
     * Retourne les meilleurs produits d'une catégorie, triés par grade nutritionnel.
     *
     * @param categoryName le nom de la catégorie
     * @param limit        le nombre maximum de résultats
     * @return les produits trouvés
     */
    List<Produit> findTopByCategory(String categoryName, int limit);

    /**
     * Retourne les meilleurs produits d'une marque et d'une catégorie, triés par grade nutritionnel.
     *
     * @param brandName    le nom de la marque
     * @param categoryName le nom de la catégorie
     * @param limit        le nombre maximum de résultats
     * @return les produits trouvés
     */
    List<Produit> findTopByBrandAndCategory(String brandName, String categoryName, int limit);
}
