package fr.etl_off.service.impl;

import fr.etl_off.dao.MarqueDao;
import fr.etl_off.model.Marque;
import fr.etl_off.service.MarqueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du service métier pour les marques.
 */
@Service
@Transactional
public class MarqueServiceImpl extends AbstractGenericService<Marque, Long> implements MarqueService {

    private final MarqueDao marqueDao;

    public MarqueServiceImpl(MarqueDao marqueDao) {
        super(marqueDao);
        this.marqueDao = marqueDao;
    }

    @Override
    @Transactional
    public Marque getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        return marqueDao.findByNom(nom)
                .orElseGet(() -> marqueDao.save(newMarque(nom)));
    }

    private Marque newMarque(String nom) {
        Marque marque = new Marque();
        marque.setNom(nom);
        return marque;
    }
}
