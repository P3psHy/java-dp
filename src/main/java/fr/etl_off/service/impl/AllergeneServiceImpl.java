package fr.etl_off.service.impl;

import fr.etl_off.cache.AllergeneCache;
import fr.etl_off.dao.AllergeneDao;
import fr.etl_off.model.Allergene;
import fr.etl_off.service.AllergeneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation du service métier pour les allergènes.
 * Utilise un cache mémoire pour éviter les requêtes SQL répétées.
 */
@Service
@Transactional
public class AllergeneServiceImpl extends AbstractGenericService<Allergene, Long> implements AllergeneService {

    private final AllergeneDao allergeneDao;
    private final AllergeneCache allergeneCache;

    public AllergeneServiceImpl(AllergeneDao allergeneDao, AllergeneCache allergeneCache) {
        super(allergeneDao);
        this.allergeneDao = allergeneDao;
        this.allergeneCache = allergeneCache;
    }

    @Override
    @Transactional
    public synchronized Allergene getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        Allergene cached = allergeneCache.getByNom(nom);
        if (cached != null) {
            return cached;
        }
        Allergene allergene = allergeneDao.findByNom(nom)
                .orElseGet(() -> allergeneDao.save(newAllergene(nom)));
        allergeneCache.put(allergene);
        return allergene;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findTop(int limit) {
        return allergeneDao.findTopAllergenes(limit);
    }

    private Allergene newAllergene(String nom) {
        Allergene allergene = new Allergene();
        allergene.setNom(nom);
        return allergene;
    }
}