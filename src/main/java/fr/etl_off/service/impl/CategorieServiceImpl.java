package fr.etl_off.service.impl;

import fr.etl_off.dao.CategorieDao;
import fr.etl_off.model.Categorie;
import fr.etl_off.service.CategorieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du service métier pour les catégories.
 */
@Service
@Transactional
public class CategorieServiceImpl extends AbstractGenericService<Categorie, Long> implements CategorieService {

    private final CategorieDao categorieDao;

    public CategorieServiceImpl(CategorieDao categorieDao) {
        super(categorieDao);
        this.categorieDao = categorieDao;
    }

    @Override
    @Transactional
    public Categorie getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        return categorieDao.findByNom(nom)
                .orElseGet(() -> categorieDao.save(newCategorie(nom)));
    }

    private Categorie newCategorie(String nom) {
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        return categorie;
    }
}
