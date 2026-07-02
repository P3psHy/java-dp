package fr.etl_off.service.impl;

import fr.etl_off.cache.MarqueCache;
import fr.etl_off.dao.MarqueDao;
import fr.etl_off.model.Marque;
import fr.etl_off.service.MarqueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du service métier pour les marques.
 * Utilise un cache mémoire pour éviter les requêtes SQL répétées.
 */
@Service
@Transactional
public class MarqueServiceImpl extends AbstractGenericService<Marque, Long> implements MarqueService {

    private final MarqueDao marqueDao;
    private final MarqueCache marqueCache;

    public MarqueServiceImpl(MarqueDao marqueDao, MarqueCache marqueCache) {
        super(marqueDao);
        this.marqueDao = marqueDao;
        this.marqueCache = marqueCache;
    }

    @Override
    @Transactional
    public Marque getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        Marque cached = marqueCache.getByNom(nom);
        if (cached != null) {
            return cached;
        }
        Marque marque = marqueDao.findByNom(nom)
                .orElseGet(() -> marqueDao.save(newMarque(nom)));
        marqueCache.put(marque);
        return marque;
    }

    private Marque newMarque(String nom) {
        Marque marque = new Marque();
        marque.setNom(nom);
        return marque;
    }
}