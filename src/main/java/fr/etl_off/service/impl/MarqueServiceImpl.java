package fr.etl_off.service.impl;

import fr.etl_off.cache.MarqueCache;
import fr.etl_off.repository.MarqueRepository;
import fr.etl_off.model.Marque;
import fr.etl_off.service.MarqueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Impl�mentation du service m�tier pour les marques.
 * Utilise un cache m�moire pour �viter les requ�tes SQL r�p�t�es.
 */
@Service
@Transactional
public class MarqueServiceImpl extends AbstractGenericService<Marque, Long> implements MarqueService {

    private final MarqueRepository marqueRepository;
    private final MarqueCache marqueCache;

    public MarqueServiceImpl(MarqueRepository marqueRepository, MarqueCache marqueCache) {
        super(marqueRepository);
        this.marqueRepository = marqueRepository;
        this.marqueCache = marqueCache;
    }

    @Override
    @Transactional
    public synchronized Marque getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        Marque cached = marqueCache.getByNom(nom);
        if (cached != null) {
            return cached;
        }
        Marque marque = marqueRepository.findByNom(nom)
                .orElseGet(() -> marqueRepository.save(newMarque(nom)));
        marqueCache.put(marque);
        return marque;
    }

    private Marque newMarque(String nom) {
        Marque marque = new Marque();
        marque.setNom(nom);
        return marque;
    }
}
