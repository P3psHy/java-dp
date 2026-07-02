package fr.etl_off.service.impl;

import fr.etl_off.dao.AllergeneDao;
import fr.etl_off.model.Allergene;
import fr.etl_off.service.AllergeneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation du service métier pour les allergènes.
 */
@Service
@Transactional
public class AllergeneServiceImpl extends AbstractGenericService<Allergene, Long> implements AllergeneService {

    private final AllergeneDao allergeneDao;

    public AllergeneServiceImpl(AllergeneDao allergeneDao) {
        super(allergeneDao);
        this.allergeneDao = allergeneDao;
    }

    @Override
    @Transactional
    public Allergene getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        return allergeneDao.findByNom(nom)
                .orElseGet(() -> allergeneDao.save(newAllergene(nom)));
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
