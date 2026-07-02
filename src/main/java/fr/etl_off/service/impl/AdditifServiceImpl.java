package fr.etl_off.service.impl;

import fr.etl_off.cache.AdditifCache;
import fr.etl_off.dao.AdditifDao;
import fr.etl_off.model.Additif;
import fr.etl_off.service.AdditifService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation du service métier pour les additifs.
 * Utilise un cache mémoire pour éviter les requêtes SQL répétées.
 */
@Service
@Transactional
public class AdditifServiceImpl extends AbstractGenericService<Additif, Long> implements AdditifService {

    private final AdditifDao additifDao;
    private final AdditifCache additifCache;

    public AdditifServiceImpl(AdditifDao additifDao, AdditifCache additifCache) {
        super(additifDao);
        this.additifDao = additifDao;
        this.additifCache = additifCache;
    }

    @Override
    @Transactional
    public synchronized Additif getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        Additif cached = additifCache.getByNom(nom);
        if (cached != null) {
            return cached;
        }
        Additif additif = additifDao.findByNom(nom)
                .orElseGet(() -> additifDao.save(newAdditif(nom)));
        additifCache.put(additif);
        return additif;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findTop(int limit) {
        return additifDao.findTopAdditifs(limit);
    }

    private Additif newAdditif(String nom) {
        Additif additif = new Additif();
        additif.setNom(nom);
        return additif;
    }
}