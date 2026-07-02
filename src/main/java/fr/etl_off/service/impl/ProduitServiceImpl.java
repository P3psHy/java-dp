package fr.etl_off.service.impl;

import fr.etl_off.dao.ProduitDao;
import fr.etl_off.model.Categorie;
import fr.etl_off.model.Marque;
import fr.etl_off.model.Produit;
import fr.etl_off.service.CategorieService;
import fr.etl_off.service.MarqueService;
import fr.etl_off.service.ProduitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Implémentation du service métier pour les produits.
 */
@Service
@Transactional
public class ProduitServiceImpl extends AbstractGenericService<Produit, Long> implements ProduitService {

    private final ProduitDao produitDao;
    private final MarqueService marqueService;
    private final CategorieService categorieService;

    public ProduitServiceImpl(ProduitDao produitDao,
                              MarqueService marqueService,
                              CategorieService categorieService) {
        super(produitDao);
        this.produitDao = produitDao;
        this.marqueService = marqueService;
        this.categorieService = categorieService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> findTopByBrand(String brandName, int limit) {
        Marque marque = marqueService.getOrCreate(brandName);
        if (marque == null || marque.getId() == null) {
            return Collections.emptyList();
        }
        return produitDao.findTopByMarque(marque.getId(), limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> findTopByCategory(String categoryName, int limit) {
        Categorie categorie = categorieService.getOrCreate(categoryName);
        if (categorie == null || categorie.getId() == null) {
            return Collections.emptyList();
        }
        return produitDao.findTopByCategorie(categorie.getId(), limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> findTopByBrandAndCategory(String brandName, String categoryName, int limit) {
        Marque marque = marqueService.getOrCreate(brandName);
        Categorie categorie = categorieService.getOrCreate(categoryName);
        if (marque == null || marque.getId() == null || categorie == null || categorie.getId() == null) {
            return Collections.emptyList();
        }
        return produitDao.findTopByMarqueAndCategorie(marque.getId(), categorie.getId(), limit);
    }
}
