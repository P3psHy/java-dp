package fr.etl_off.dao;

import fr.etl_off.model.Produit;

import java.util.List;

/**
 * DAO dédié à l'entité {@link Produit}.
 */
public interface ProduitDao extends GenericDao<Produit, Long> {

    /**
     * Recherche les produits d'une marque donnée, triés par leur note nutritionnelle.
     * L'ordre est croissant sur le grade (A, B, C...).
     *
     * @param marqueId l'identifiant de la marque
     * @param limit    le nombre maximum de résultats
     * @return les produits trouvés
     */
    List<Produit> findTopByMarque(Long marqueId, int limit);

    /**
     * Recherche les produits d'une catégorie donnée, triés par leur note nutritionnelle.
     *
     * @param categorieId l'identifiant de la catégorie
     * @param limit       le nombre maximum de résultats
     * @return les produits trouvés
     */
    List<Produit> findTopByCategorie(Long categorieId, int limit);

    /**
     * Recherche les produits d'une marque et d'une catégorie données, triés par leur note nutritionnelle.
     *
     * @param marqueId    l'identifiant de la marque
     * @param categorieId l'identifiant de la catégorie
     * @param limit       le nombre maximum de résultats
     * @return les produits trouvés
     */
    List<Produit> findTopByMarqueAndCategorie(Long marqueId, Long categorieId, int limit);
}
