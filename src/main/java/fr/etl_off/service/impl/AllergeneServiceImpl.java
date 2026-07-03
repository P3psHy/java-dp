package fr.etl_off.service.impl;

import fr.etl_off.cache.AllergeneCache;
import fr.etl_off.repository.AllergeneRepository;
import org.springframework.data.domain.PageRequest;
import fr.etl_off.model.Allergene;
import fr.etl_off.service.AllergeneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Impl�mentation du service m�tier pour les allerg�nes.
 * Utilise un cache m�moire pour �viter les requ�tes SQL r�p�t�es.
 */
@Service
@Transactional
public class AllergeneServiceImpl extends AbstractGenericService<Allergene, Long> implements AllergeneService {

    private final AllergeneRepository allergeneRepository;
    private final AllergeneCache allergeneCache;

    public AllergeneServiceImpl(AllergeneRepository allergeneRepository, AllergeneCache allergeneCache) {
        super(allergeneRepository);
        this.allergeneRepository = allergeneRepository;
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
        Allergene allergene = allergeneRepository.findByNom(nom)
                .orElseGet(() -> allergeneRepository.save(newAllergene(nom)));
        allergeneCache.put(allergene);
        return allergene;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findTop(int limit) {
        return allergeneRepository.findTopAllergenes(PageRequest.of(0, limit));
    }

    private Allergene newAllergene(String nom) {
        Allergene allergene = new Allergene();
        allergene.setNom(nom);
        return allergene;
    }
}
