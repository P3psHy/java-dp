package fr.etl_off.service.impl;

import fr.etl_off.cache.CategorieCache;
import fr.etl_off.repository.CategorieRepository;
import fr.etl_off.model.Categorie;
import fr.etl_off.service.CategorieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Impl�mentation du service m�tier pour les cat�gories.
 * Utilise un cache m�moire pour �viter les requ�tes SQL r�p�t�es.
 */
@Service
@Transactional
public class CategorieServiceImpl extends AbstractGenericService<Categorie, Long> implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final CategorieCache categorieCache;

    public CategorieServiceImpl(CategorieRepository categorieRepository, CategorieCache categorieCache) {
        super(categorieRepository);
        this.categorieRepository = categorieRepository;
        this.categorieCache = categorieCache;
    }

    @Override
    @Transactional
    public synchronized Categorie getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        Categorie cached = categorieCache.getByNom(nom);
        if (cached != null) {
            return cached;
        }
        Categorie categorie = categorieRepository.findByNom(nom)
                .orElseGet(() -> categorieRepository.save(newCategorie(nom)));
        categorieCache.put(categorie);
        return categorie;
    }

    private Categorie newCategorie(String nom) {
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        return categorie;
    }
}
