package fr.etl_off.service.impl;

import fr.etl_off.model.Categorie;
import fr.etl_off.model.Marque;
import fr.etl_off.model.Produit;
import fr.etl_off.repository.ProduitRepository;
import fr.etl_off.service.CategorieService;
import fr.etl_off.service.MarqueService;
import fr.etl_off.service.ProduitService;
import org.springframework.data.domain.PageRequest;
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

    private final ProduitRepository produitRepository;
    private final MarqueService marqueService;
    private final CategorieService categorieService;

    public ProduitServiceImpl(ProduitRepository produitRepository,
                              MarqueService marqueService,
                              CategorieService categorieService) {
        super(produitRepository);
        this.produitRepository = produitRepository;
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
        return produitRepository.findTopByMarque(marque.getId(), PageRequest.of(0, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> findTopByCategory(String categoryName, int limit) {
        Categorie categorie = categorieService.getOrCreate(categoryName);
        if (categorie == null || categorie.getId() == null) {
            return Collections.emptyList();
        }
        return produitRepository.findTopByCategorie(categorie.getId(), PageRequest.of(0, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> findTopByBrandAndCategory(String brandName, String categoryName, int limit) {
        Marque marque = marqueService.getOrCreate(brandName);
        Categorie categorie = categorieService.getOrCreate(categoryName);
        if (marque == null || marque.getId() == null || categorie == null || categorie.getId() == null) {
            return Collections.emptyList();
        }
        return produitRepository.findTopByMarqueAndCategorie(marque.getId(), categorie.getId(), PageRequest.of(0, limit));
    }
}
