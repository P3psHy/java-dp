package fr.etl_off.service.impl;

import fr.etl_off.cache.CategorieCache;
import fr.etl_off.dao.CategorieDao;
import fr.etl_off.model.Categorie;
import fr.etl_off.service.CategorieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du service métier pour les catégories.
 * Utilise un cache mémoire pour éviter les requêtes SQL répétées.
 */
@Service
@Transactional
public class CategorieServiceImpl extends AbstractGenericService<Categorie, Long> implements CategorieService {

    private final CategorieDao categorieDao;
    private final CategorieCache categorieCache;

    public CategorieServiceImpl(CategorieDao categorieDao, CategorieCache categorieCache) {
        super(categorieDao);
        this.categorieDao = categorieDao;
        this.categorieCache = categorieCache;
    }

    @Override
    @Transactional
    public Categorie getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        Categorie cached = categorieCache.getByNom(nom);
        if (cached != null) {
            return cached;
        }
        Categorie categorie = categorieDao.findByNom(nom)
                .orElseGet(() -> categorieDao.save(newCategorie(nom)));
        categorieCache.put(categorie);
        return categorie;
    }

    private Categorie newCategorie(String nom) {
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        return categorie;
    }
}