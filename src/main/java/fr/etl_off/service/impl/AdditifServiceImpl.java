package fr.etl_off.service.impl;

import fr.etl_off.cache.AdditifCache;
import fr.etl_off.repository.AdditifRepository;
import org.springframework.data.domain.PageRequest;
import fr.etl_off.model.Additif;
import fr.etl_off.service.AdditifService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Impl�mentation du service m�tier pour les additifs.
 * Utilise un cache m�moire pour �viter les requ�tes SQL r�p�t�es.
 */
@Service
@Transactional
public class AdditifServiceImpl extends AbstractGenericService<Additif, Long> implements AdditifService {

    private final AdditifRepository additifRepository;
    private final AdditifCache additifCache;

    public AdditifServiceImpl(AdditifRepository additifRepository, AdditifCache additifCache) {
        super(additifRepository);
        this.additifRepository = additifRepository;
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
        Additif additif = additifRepository.findByNom(nom)
                .orElseGet(() -> additifRepository.save(newAdditif(nom)));
        additifCache.put(additif);
        return additif;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findTop(int limit) {
        return additifRepository.findTopAdditifs(PageRequest.of(0, limit));
    }

    private Additif newAdditif(String nom) {
        Additif additif = new Additif();
        additif.setNom(nom);
        return additif;
    }
}
