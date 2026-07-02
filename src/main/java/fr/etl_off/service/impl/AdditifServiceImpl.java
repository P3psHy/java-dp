package fr.etl_off.service.impl;

import fr.etl_off.dao.AdditifDao;
import fr.etl_off.model.Additif;
import fr.etl_off.service.AdditifService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation du service métier pour les additifs.
 */
@Service
@Transactional
public class AdditifServiceImpl extends AbstractGenericService<Additif, Long> implements AdditifService {

    private final AdditifDao additifDao;

    public AdditifServiceImpl(AdditifDao additifDao) {
        super(additifDao);
        this.additifDao = additifDao;
    }

    @Override
    @Transactional
    public Additif getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        return additifDao.findByNom(nom)
                .orElseGet(() -> additifDao.save(newAdditif(nom)));
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
